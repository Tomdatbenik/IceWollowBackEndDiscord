package serverlogic.dal.repo;

import serverlogic.interfaces.IServerContext;
import serverlogic.interfaces.IServerRepo;
import serverlogic.models.IWServer;

public class ServerRepo implements IServerRepo {
    private IServerContext context;

    public ServerRepo(IServerContext context) {
        this.context = context;
    }

    @Override
    public boolean updateServer(IWServer server) {
        return context.updateServer(server);
    }
}
