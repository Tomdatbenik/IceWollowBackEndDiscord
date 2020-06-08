package restmodule.controllers;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restmodule.models.IWServer;
import restmodule.models.User;
import restmodule.models.dtomodels.JoinServerDto;
import restmodule.models.dtomodels.LeaveServerDto;
import restmodule.models.dtomodels.ServerDTO;
import restmodule.service.ServerService;
import restmodule.service.UserService;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path= "server", produces = MediaType.APPLICATION_JSON_VALUE)
public class ServerController {

    ServerService serverService;

    UserService userService;

    private Gson gson = new Gson();

    @CrossOrigin(origins = {"*"})
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

    @CrossOrigin(origins = {"*"})
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

    @CrossOrigin(origins = {"*"})
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

    @CrossOrigin(origins = {"*"})
    @PutMapping(value = "/join")
    public ResponseEntity joinServer(@RequestBody LeaveServerDto leaveServer)
    {
        IWServer server = serverService.getServerById(leaveServer.getServer_id());

        if(server == null)
        {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        else if(leaveServer.getUser().getId() == 0)
        {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        else
        {
            if(server.getUsers().stream().filter(u->u.getId() == leaveServer.getUser().getId()).findAny().orElse(null) != null)
            {
                return new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY);
            }

            server.getUsers().remove(leaveServer.getUser());

            serverService.updateServer(server);

            return new ResponseEntity(HttpStatus.OK);
        }
    }

    @CrossOrigin(origins = {"*"})
    @PutMapping(value = "/leave")
    public ResponseEntity leaveServer(@RequestBody JoinServerDto joinServer)
    {
        IWServer server = serverService.getServerByCode(joinServer.getCode());

        if(server == null)
        {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        else if(joinServer.getUser().getId() == 0)
        {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        else
        {
            if(server.getUsers().stream().filter(u->u.getId() == joinServer.getUser().getId()).findAny().orElse(null) != null)
            {
                return new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY);
            }

            server.getUsers().add(joinServer.getUser());

            serverService.updateServer(server);

            return new ResponseEntity(HttpStatus.OK);
        }
    }
}
