package server.controllers;

import com.google.gson.Gson;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import serverlogic.logic.ServerContainerLogic;
import serverlogic.models.IWServer;
import serverlogic.models.dtomodels.ServerDTO;
import userlogic.logic.UserContainerLogic;
import userlogic.models.User;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path= "server", produces = MediaType.APPLICATION_JSON_VALUE)
public class ServerContainerController {

    ServerContainerLogic serverContainerLogic = new ServerContainerLogic();

    private UserContainerLogic userContainerLogic = new UserContainerLogic();
    private Gson gson = new Gson();
    @PostMapping(value = "/add")
    public ServerDTO addServer(String server)
    {
        IWServer iwServer = gson.fromJson(server, IWServer.class);

        User user = userContainerLogic.getUserByEmail(iwServer.getOwner().getEmail());
        iwServer.setOwner(user);

        return new ServerDTO(serverContainerLogic.addServer(iwServer));
    }

    @GetMapping(value = "/getbyuser")
    public List<ServerDTO> getallservers(String email)
    {
        User user = userContainerLogic.getUserByEmail(email);
        List<IWServer> servers =  serverContainerLogic.getAllServersByUser(user);
        List<ServerDTO> serverDTOS = new ArrayList<>();

        if(servers != null)
        {
            servers.stream().forEach(s-> serverDTOS.add(new ServerDTO(s)));
        }

        return serverDTOS;
    }
}
