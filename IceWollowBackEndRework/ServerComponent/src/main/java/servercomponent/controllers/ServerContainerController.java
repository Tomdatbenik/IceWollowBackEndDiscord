package servercomponent.controllers;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import servercomponent.models.IWServer;
import servercomponent.models.dtomodels.ServerDTO;
import servercomponent.service.ServerService;
import usercomponent.models.User;
import usercomponent.service.UserService;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping(path= "server", produces = MediaType.APPLICATION_JSON_VALUE)
public class ServerContainerController {

    ServerService serverService;

    UserService userService;


    private Gson gson = new Gson();
    @PostMapping(value = "/add")
    public ServerDTO addServer(String server)
    {
        IWServer iwServer = gson.fromJson(server, IWServer.class);

        User user = userService.getUserByEmail(iwServer.getOwner().getEmail());
        iwServer.setOwner(user);

        return new ServerDTO(serverService.addServer(iwServer));
    }

    @GetMapping(value = "/getbyuser")
    public List<ServerDTO> getallservers(String email)
    {
        User user = userService.getUserByEmail(email);
        List<IWServer> servers =  serverService.getAllServersByUser(user);
        List<ServerDTO> serverDTOS = new ArrayList<>();

        if(servers != null)
        {
            servers.stream().forEach(s-> serverDTOS.add(new ServerDTO(s)));
        }

        return serverDTOS;
    }
}
