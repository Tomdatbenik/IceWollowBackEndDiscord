package websocketserver.managers;

import org.eclipse.jetty.websocket.api.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import serverlogic.logic.ServerContainerLogic;
import serverlogic.models.Channel;
import serverlogic.models.IWServer;
import serverlogic.models.VoiceChannel;
import userlogic.models.User;
import websocketserver.messages.BaseMessage;
import websocketserver.models.Client;
import websocketserver.models.ServerObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class ServerManager {

    private final Logger logger = LoggerFactory.getLogger(ServerManager.class);

    private static ServerManager instance;

    public static ServerManager getInstance() {
        if (instance == null) {
            instance = new ServerManager();
        }
        return instance;
    }

    private final ServerContainerLogic serverContainerLogic = new ServerContainerLogic();

    private final List<ServerObserver> observers = new ArrayList<>();

    private final List<IWServer> activeServers = new ArrayList<>();

    public List<ServerObserver> getObservers() {
        return observers;
    }

    public void subscribeClientToServer(Client client, int serverId) {
        Client handlingClient = client;
        boolean clientExists = observers.stream().anyMatch(o -> o.getClient().getUser().getId() == handlingClient.getUser().getId());

        ServerObserver observer = new ServerObserver(client);

        if (clientExists) {
            observer = observers.stream()
                    .filter(o -> o.getClient().getUser().getId() == handlingClient.getUser().getId())
                    .findAny().orElse(null);
        } else {
            observers.add(observer);
        }

        IWServer server = activeServers.stream().filter(s -> s.getId() == serverId).findAny().orElse(null);

        if (server != null) {
            observer.setServer(server);
        } else {
            server = serverContainerLogic.getServerById(serverId);

            observer.setServer(server);
            activeServers.add(server);
        }

        logger.info(client.getUser().getDisplayName() + " Subscribed to " + server.getName());

        updateObserversByServer(server);
        cleanActiveServers();
    }

    public void subscribeClientToChannel(Client client, int channelId) {
        ServerObserver observer = getObserverByUserId(client.getUser().getId());

        IWServer server = activeServers.stream().filter(s -> s.getChannels().stream().filter(c -> c.getId() == channelId) != null).findAny().orElse(null);
        if (server != null) {
            VoiceChannel channel = (VoiceChannel) server.getChannels().stream().filter(c -> c.getId() == channelId).findAny().orElse(null);

            if (channel.getUsers().stream().filter(u -> u.getId() == client.getUser().getId()).findAny().orElse(null) == null) {
                channel.getUsers().add(client.getUser());
                observer.setChannel(channel);
                logger.info(client.getUser().getDisplayName() + " subscribed to channel: " + channel.getName());
            }

            //Makes sure that the person that left doesnt case a notification when he joins the same channel with no other users
            cleanSubscribedChannels();

            if (channel.getUsers().size() > 1) {
                BaseMessage message = new BaseMessage();
                message.setHandler("CreatePeerHandlerWithInitiator");
                client.sendMessage(message);
                logger.info("Send create peer message to " + client.getUser().getDisplayName());
            }


        }
    }

    public void removeClient(Client client) {
        ServerObserver observer = observers.stream().filter(o -> o.getClient() == client).findAny().orElse(null);

        if (observer != null) {
            observers.remove(observer);
        }

        cleanActiveServers();
        cleanSubscribedChannels();
    }

    private void cleanActiveServers() {
        List<IWServer> inactiveServers = new ArrayList<>();
        activeServers.stream().forEach(s -> {

            AtomicBoolean serverIsActive = new AtomicBoolean(false);
            observers.stream().forEach(o -> {
                if (o.getServer() == s) {
                    serverIsActive.set(true);
                }
            });

            if (!serverIsActive.get()) {
                inactiveServers.add(s);
            }
        });

        inactiveServers.stream().forEach(s -> {
                    logger.info("Removed: " + s.getName() + " from active servers");
                    activeServers.remove(s);
                }
        );
    }

    private void cleanSubscribedChannels() {
        activeServers.stream().forEach(s -> {
            s.getVoiceChannels().stream().forEach(c -> {
                List<User> removeUsers = new ArrayList<>();

                observers.stream().forEach(o -> {
                    List<User> users = c.getUsers().stream().filter(u -> u.getId() == o.getClient().getUser().getId()).collect(Collectors.toList());
                    if (o.getChannel().getId() != c.getId() && users != null) {

                        users.stream().forEach(u -> {
                            removeUsers.add(u);
                        });
                    }
                });

                removeUsers.stream().forEach(u -> {
                    c.getUsers().remove(c.getUsers().stream().filter(user -> user.getId() == u.getId()).findAny().orElse(null));
                    logger.info("Removed " + u.getDisplayName() + " From channel " + c.getName());
                    updateObserversUserLeftChannel(c, u);
                });

                updateObserversByServer(s);

            });
        });
    }

    private void updateObserversUserLeftChannel(VoiceChannel channel, User user) {
        List<ServerObserver> observerList = getObserversByChannel(channel);

        observerList.stream().forEach(o -> {
            BaseMessage message = new BaseMessage();
            message.setHandler("UserLeftChannelHandler");
            message.setContent(String.valueOf(user.getId()));
            o.getClient().sendMessage(message);
        });
    }

    private void updateObserversByServer(IWServer server) {
        observers.stream().forEach(o -> {
            if (o.getServer() == server) {
                o.update();
            }
        });
    }

    public ServerObserver getObserverByUserId(int userId) {
        return observers.stream().filter(o -> o.getClient().getUser().getId() == userId).findAny().orElse(null);
    }

    public ServerObserver getObserverBySession(Session session) {
        return observers.stream().filter(o -> o.getClient().getSession() == session).findAny().orElse(null);
    }

    private List<ServerObserver> getObserversByServer(IWServer server) {
        return observers.stream().filter(o -> o.getServer().getId() == server.getId()).collect(Collectors.toList());
    }

    private List<ServerObserver> getObserversByChannel(Channel channel) {
        return observers.stream().filter(o -> o.getChannel().getId() == channel.getId()).collect(Collectors.toList());
    }

}
