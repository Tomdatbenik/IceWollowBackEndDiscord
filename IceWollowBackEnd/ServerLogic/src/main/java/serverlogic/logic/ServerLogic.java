package serverlogic.logic;

import serverlogic.factories.ServerFactory;
import serverlogic.interfaces.IServerRepo;
import serverlogic.models.IWServer;

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
