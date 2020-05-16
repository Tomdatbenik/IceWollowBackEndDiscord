package servercomponent.controllers;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
public class ServerController {

    ServerService serverService;

    UserService userService;

    private Gson gson = new Gson();
    @PostMapping(value = "/add")
    public ResponseEntity<ServerDTO> addServer(String server)
    {
        IWServer iwServer = gson.fromJson(server, IWServer.class);

        User user = userService.getUserByEmail(iwServer.getOwner().getEmail());
        iwServer.setOwner(user);
        iwServer.getUsers().add(user);

        ServerDTO serverDTO = new ServerDTO(serverService.addServer(iwServer));

        return new ResponseEntity(serverDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/getbyuser")
    public ResponseEntity<List<ServerDTO>> getallservers(String email)
    {
        User user = userService.getUserByEmail(email);
        List<IWServer> servers =  serverService.getAllServersByUser(user);
        List<ServerDTO> serverDTOS = new ArrayList<>();

        if(servers != null)
        {
            servers.stream().forEach(s-> serverDTOS.add(new ServerDTO(s)));
        }

        return new ResponseEntity(servers,HttpStatus.OK);
    }

    @GetMapping(value = "/invitecode")
    public ResponseEntity getInviteCode(int server_id)
    {
        IWServer server = serverService.getServerById(server_id);

        if(server != null)
        {
            String code = server.getCode();
            return new ResponseEntity(code ,HttpStatus.OK);
        }

        return new ResponseEntity(null, HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/join")
    public ResponseEntity joinServer(@RequestBody User user, String inviteCode)
    {
        IWServer joinServer = serverService.getServerByCode(inviteCode);

        if(joinServer == null)
        {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        else if(user.getId() == 0)
        {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        else
        {
            joinServer.getUsers().add(user);
            return new ResponseEntity(HttpStatus.OK);
        }
    }
}
