package server.logic;

import server.factories.ServerFactory;
import server.interfaces.IServerRepo;
import server.models.IWServer;

public class ServerLogic {

    private ServerFactory serverFactory;
    private IServerRepo repo;

    public ServerLogic() {
        serverFactory = ServerFactory.getInstance();
        repo = serverFactory.getServerRepo();
    }

    public boolean updateServer(IWServer server) {
        return repo.updateServer(server);
    }
}
