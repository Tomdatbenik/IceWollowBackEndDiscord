package server.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import serverlogic.logic.ServerContainerLogic;
import serverlogic.logic.ServerLogic;
import serverlogic.models.IWServer;
import userlogic.logic.UserContainerLogic;
import userlogic.models.User;

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
