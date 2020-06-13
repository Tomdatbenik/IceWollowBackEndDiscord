package serverwebsocket.managers;

import com.google.gson.JsonElement;
import lombok.AllArgsConstructor;
import org.eclipse.jetty.websocket.api.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import restmodule.models.Channel;
import restmodule.models.IWServer;
import restmodule.models.User;
import restmodule.models.VoiceChannel;
import restmodule.service.ServerService;
import serverwebsocket.messages.BaseMessage;
import serverwebsocket.models.Client;
import serverwebsocket.models.ServerObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ServerManager {

    private final Logger logger = LoggerFactory.getLogger(ServerManager.class);

    private static ServerManager instance;

    public static ServerManager getInstance(ServerService service) {
        if (instance == null) {
            instance = new ServerManager(service);
        }
        return instance;
    }

    @Autowired
    private final ServerService serverContainerLogic;

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
            if(observer.getServer() == server)
            {
                IWServer foundServer = serverContainerLogic.getServerById(serverId);

                if(foundServer!= null)
                {
                    observer.setServer(foundServer);
                }
            }
            else
            {
                observer.setServer(server);
            }
        } else {
            server = serverContainerLogic.getServerById(serverId);

            observer.setServer(server);
            activeServers.add(server);
        }

        logger.info(client.getUser().getDisplayName() + " Subscribed to " + server.getName());

        updateObserversByServer(server);
        cleanActiveServers();
    }

    public void unsubscribeClientToChannel(Client client, int channelId) {
        ServerObserver observer = getObserverByUserId(client.getUser().getId());

        IWServer server = activeServers.stream().filter(s -> s.getChannels().stream().filter(c -> c.getId() == channelId) != null).findAny().orElse(null);
        if (server != null) {
            VoiceChannel channel = (VoiceChannel) server.getChannels().stream().filter(c -> c.getId() == channelId).findAny().orElse(null);

            if (channel.getUsers().stream().filter(u -> u.getId() == client.getUser().getId()).findAny().orElse(null) != null) {
                channel.getUsers().remove(client.getUser());
                observer.setChannel(channel);
                logger.info(client.getUser().getDisplayName() + " unsubscribed from channel: " + channel.getName());
            }

            //Makes sure that the person that left doesnt case a notification when he joins the same channel with no other users
            updateObserversByServer(server);
        }
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

                    if(o.getChannel() != null)
                    {
                        if (o.getChannel().getId() != c.getId() && users != null) {

                            users.stream().forEach(u -> {
                                removeUsers.add(u);
                            });
                        }
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

    public void connectPeerInitPeer(Client client, JsonElement rtc_id) {
        VoiceChannel channel = getChannelByClient(client);

        IWServer server = getServerbyChannel(channel);

        server.getUsers().stream().forEach(user -> {
            if (user.getId() != client.getUser().getId()) {
                ServerObserver serverObserver = getObserverByUserId(user.getId());

                BaseMessage message = new BaseMessage();
                message.setContent(rtc_id.toString());
                message.setHandler("InitIdHandler");

                serverObserver.getClient().sendMessage(message);
            }
        });
    }

    private IWServer getServerbyChannel(Channel channel) {
        return activeServers.stream().filter(s -> s.getChannels().stream().filter(c -> c.getId() == channel.getId()) != null).findAny().orElse(null);
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

    private VoiceChannel getChannelByClient(Client client) {
        AtomicReference<VoiceChannel> voiceChannel = new AtomicReference<>();

        activeServers.stream().forEach(server -> {
            server.getVoiceChannels().stream().forEach(channel -> {
                User user = channel.getUsers().stream().filter(u -> u.getId() == client.getUser().getId()).findAny().orElse(null);

                if (user != null) {
                    voiceChannel.set(channel);
                }
            });
        });

        return voiceChannel.get();
    }

    private List<ServerObserver> getObserversByChannel(Channel channel) {
        return observers.stream().filter(o -> o.getChannel().getId() == channel.getId()).collect(Collectors.toList());
    }
}
