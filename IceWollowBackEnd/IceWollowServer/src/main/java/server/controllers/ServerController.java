package server.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.logic.ServerContainerLogic;
import server.logic.ServerLogic;
import server.models.IWServer;
import user.logic.UserContainerLogic;
import user.models.User;

@RestController
@RequestMapping(path= "server", produces = MediaType.APPLICATION_JSON_VALUE)
public class ServerController {

    ServerLogic serverLogic = new ServerLogic();
    ServerContainerLogic serverContainerLogic = new ServerContainerLogic();

    private UserContainerLogic userContainerLogic = new UserContainerLogic();

    @PostMapping(value = "/leave")
    public boolean leaveServer(String email, int serverid)
    {
        User user = userContainerLogic.getUserByEmail(email);
        IWServer server = serverContainerLogic.getServerById(serverid);

        User findUser = server.getUsers().stream().filter(u -> u.getId() == user.getId()).findAny().orElse(null);
        server.getUsers().remove(findUser);

        return serverLogic.updateServer(server);
    }
}
