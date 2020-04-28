package websocketserver.managers;

import server.logic.ServerContainerLogic;
import server.models.IWServer;
import websocketserver.models.Client;
import websocketserver.models.ServerObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class ServerManager {

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

        updateObserversByServer(server);
        cleanActiveServers();
    }

    public void removeClient(Client client) {
        ServerObserver observer = observers.stream().filter(o -> o.getClient() == client).findAny().orElse(null);

        if (observer != null) {
            observers.remove(observer);
        }

        cleanActiveServers();
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

        inactiveServers.stream().forEach(s -> activeServers.remove(s));

    }

    private void updateObserversByServer(IWServer server)
    {
        observers.stream().forEach(o -> {
            if (o.getServer() == server) {
                o.setServer(server);
                o.update();
            }
        });
    }

}
