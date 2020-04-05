package server.dal.repo;

import server.interfaces.IServerContext;
import server.interfaces.IServerRepo;
import server.models.IWServer;

public class ServerRepo implements IServerRepo {
    private IServerContext context;

    public ServerRepo(IServerContext context) {
        this.context = context;
    }

    @Override
    public boolean updateServer(IWServer server) {
        return false;
    }
}
