package serverlogic.interfaces;

import serverlogic.models.IWServer;
import userlogic.models.User;

import java.util.List;

public interface IServerContainerRepo {

    IWServer addServer(IWServer server);

    List<IWServer> getAllServersByUser(User user);

    IWServer getServerById(int id);
}
