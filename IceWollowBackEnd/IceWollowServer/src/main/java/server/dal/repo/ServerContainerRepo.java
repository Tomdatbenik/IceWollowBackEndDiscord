package server.dal.repo;

import server.interfaces.IServerContainerRepo;
import server.interfaces.IServerContext;
import server.models.IWServer;
import user.interfaces.IUserContext;
import user.models.User;

import java.util.List;

public class ServerContainerRepo implements IServerContainerRepo {
    private IServerContext context;

    public ServerContainerRepo(IServerContext context) {
        this.context = context;
    }


    @Override
    public boolean addServer(IWServer server) {
        return context.addServer(server);
    }

    @Override
    public List<IWServer> getAllServersByUser(User user) {
        return null;
    }

    @Override
    public IWServer getServerById(int id) {
        return null;
    }
}
