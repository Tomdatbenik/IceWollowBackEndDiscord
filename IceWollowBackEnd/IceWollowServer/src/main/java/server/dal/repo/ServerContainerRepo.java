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
    public IWServer addServer(IWServer server) {
        return context.addServer(server);
    }

    @Override
    public List<IWServer> getAllServersByUser(User user) {
        return context.getAllServersByUser(user);
    }

    @Override
    public IWServer getServerById(int id) {
        return context.getServerById(id);
    }
}
