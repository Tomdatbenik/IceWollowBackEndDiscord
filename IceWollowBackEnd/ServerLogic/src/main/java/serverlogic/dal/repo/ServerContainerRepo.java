package serverlogic.dal.repo;

import serverlogic.interfaces.IServerContainerRepo;
import serverlogic.interfaces.IServerContext;
import serverlogic.models.IWServer;
import userlogic.models.User;

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
