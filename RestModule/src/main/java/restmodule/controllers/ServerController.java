package restmodule.controllers;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restmodule.models.IWServer;
import restmodule.models.User;
import restmodule.models.dtomodels.AddServerDto;
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
    @PostMapping()
    public ResponseEntity<ServerDTO> addServer(@RequestBody AddServerDto server)
    {
        if(server.getName().length() < 5 || server.getOwner() == null || server.getOwner().getId() == 0)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User user = userService.getUserById(server.getOwner().getId());
        IWServer iwServer = new IWServer();
        iwServer.setName(server.getName());
        iwServer.setOwner(user);
        iwServer.getUsers().add(user);

        ServerDTO serverDTO = new ServerDTO(serverService.addServer(iwServer));

        return new ResponseEntity(serverDTO, HttpStatus.OK);
    }

    @CrossOrigin(origins = {"*"})
    @GetMapping(value = "/getbyuser")
    public ResponseEntity<List<ServerDTO>> getallservers(String email) {
        User user = userService.getUserByEmail(email);

        if (user != null)
        {
            List<IWServer> servers =  serverService.getAllServersByUser(user);
            List<ServerDTO> serverDTOS = new ArrayList<>();

            //TODO dto's dont work
            if(servers != null)
            {
                servers.stream().forEach(s-> serverDTOS.add(new ServerDTO(s)));
            }

            return new ResponseEntity(servers,HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @CrossOrigin(origins = {"*"})
    @PutMapping(value = "/join")
    public ResponseEntity joinServer(@RequestBody JoinServerDto joinServerDto)
    {
        IWServer server = serverService.getServerByCode(joinServerDto.getCode());

        if(server == null)
        {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        else if(joinServerDto.getUser().getId() == 0)
        {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        else
        {
            if(server.getUsers().stream().filter(u->u.getId() == joinServerDto.getUser().getId()).findAny().orElse(null) != null)
            {
                return new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY);
            }

            server.getUsers().add(joinServerDto.getUser());

            serverService.updateServer(server);

            return new ResponseEntity(HttpStatus.OK);
        }
    }

    @CrossOrigin(origins = {"*"})
    @PutMapping(value = "/leave")
    public ResponseEntity leaveServer(@RequestBody LeaveServerDto leaveServer)
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
            if(server.getUsers().stream().filter(u->u.getId() == leaveServer.getUser().getId()).findAny().orElse(null) == null)
            {
                return new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY);
            }

            server.getUsers().remove(leaveServer.getUser());

            if(server.getOwner().getId() == leaveServer.getUser().getId())
            {
                if(server.getUsers().size() > 1)
                {
                    User owner = server.getUsers().stream().filter(u-> u.getId() != leaveServer.getUser().getId()).findAny().orElse(null);
                    server.setOwner(owner);
                }
                else
                {
                    serverService.deleteServer(server);
                }

            }

            serverService.updateServer(server);

            return new ResponseEntity(HttpStatus.OK);
        }
    }
}
