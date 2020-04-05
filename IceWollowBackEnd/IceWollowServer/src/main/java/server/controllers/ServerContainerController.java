package server.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import server.logic.ServerContainerLogic;
import server.models.IWServer;
import user.logic.UserContainerLogic;
import user.models.User;
import user.models.datamodels.UserDataModel;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path= "server", produces = MediaType.APPLICATION_JSON_VALUE)
public class ServerContainerController {

    ServerContainerLogic serverContainerLogic = new ServerContainerLogic();

    private UserContainerLogic userContainerLogic = new UserContainerLogic();

    @PostMapping(value = "/add")
    public boolean addServer(@RequestBody UserDataModel userdm, String serverName)
    {
        User user = userContainerLogic.getUserByEmail(userdm.getEmail());

        IWServer server = new IWServer();
        server.setName(serverName);
        server.setOwner(user);
        
        serverContainerLogic.addServer(server);
    }
//    public boolean addServer(@RequestBody UserDataModel userdm)
//    {
//        User user = new User(userdm.getDisplayName(), userdm.getEmail());
//
//        return userContainerLogic.addUser(user);
//    }
}
