package server.logic;

import server.factories.ServerFactory;
import server.interfaces.IServerContainerRepo;
import server.models.*;
import user.factories.UserFactory;
import user.models.User;

import java.util.ArrayList;
import java.util.List;

public class ServerContainerLogic {

    private ServerFactory serverFactory;
    private IServerContainerRepo repo;

    public ServerContainerLogic(/*IUserContainerRepo repo*/) {
        serverFactory = ServerFactory.getInstance();
        repo = serverFactory.getServerContainerRepo();
    }

    public List<IWServer> getAllServersByUser(User user)
    {
        return repo.getAllServersByUser(user);
    }

    public boolean addServer(IWServer server)
    {
        //Add 2 channels for general chat at the start of a server creation.
        server.getTextChannels().add(new TextChannel("General"));
        server.getVoiceChannels().add(new VoiceChannel("General"));

        return repo.addServer(server);
    }
}
