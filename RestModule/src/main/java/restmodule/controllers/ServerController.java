package restmodule.controllers;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
@Log4j2
public class ServerController {

    ServerService serverService;

    UserService userService;

    @CrossOrigin(origins = {"*"})
    @PostMapping()
    public ResponseEntity<ServerDTO> addServer(@RequestBody AddServerDto server)
    {
        log.info("Start adding server: " + server.getName());
        if(server.getName().length() < 5 || server.getOwner() == null || server.getOwner().getId() == 0)
        {
            log.info("Post body is missing values");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        log.info("Getting user with id: " + server.getOwner().getId());
        User user = userService.getUserById(server.getOwner().getId());

        if(user==null)
        {
            log.info("User doesn't exist");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        log.info("User received: " + user.getDisplayName());

        IWServer iwServer = new IWServer();
        iwServer.setName(server.getName());
        iwServer.setOwner(user);
        iwServer.getUsers().add(user);
        log.info("Saving server: " + iwServer.getName());
        ServerDTO serverDTO = new ServerDTO(serverService.addServer(iwServer));

        log.info("Returning status OK, and DTO.");
        return new ResponseEntity(serverDTO, HttpStatus.OK);
    }

    @CrossOrigin(origins = {"*"})
    @GetMapping(value = "/getbyuser")
    public ResponseEntity<List<ServerDTO>> getallservers(String email) {
        log.info("Getting all server with user email: " + email);
        User user = userService.getUserByEmail(email);

        if (user != null)
        {
            List<IWServer> servers =  serverService.getAllServersByUser(user);
            List<ServerDTO> serverDTOS = new ArrayList<>();

            //TODO dto's dont work
            if(servers != null)
            {
                log.info("Found servers");
                servers.stream().forEach(s-> serverDTOS.add(new ServerDTO(s)));
            }

            log.info("Returning servers in dto list");
            return new ResponseEntity(servers,HttpStatus.OK);
        }
        log.info("User is null");
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @CrossOrigin(origins = {"*"})
    @PutMapping(value = "/join")
    public ResponseEntity joinServer(@RequestBody JoinServerDto joinServerDto)
    {
        log.info("User :" + joinServerDto.getUser().getId() + " joining server with code: " + joinServerDto.getCode());
        IWServer server = serverService.getServerByCode(joinServerDto.getCode());

        if(server == null)
        {
            log.info("No server found");
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        else if(joinServerDto.getUser().getId() == 0)
        {
            log.info("User id is not set");
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        else
        {
            if(server.getUsers().stream().filter(u->u.getId() == joinServerDto.getUser().getId()).findAny().orElse(null) != null)
            {
                log.info("Server already has user");
                return new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY);
            }

            log.info("Adding user to server users");
            server.getUsers().add(joinServerDto.getUser());

            log.info("Updating server");
            serverService.updateServer(server);

            return new ResponseEntity(HttpStatus.OK);
        }
    }

    @CrossOrigin(origins = {"*"})
    @PutMapping(value = "/leave")
    public ResponseEntity leaveServer(@RequestBody LeaveServerDto leaveServer)
    {
        log.info("User :" + leaveServer.getUser().getId() + " is leaving: " + leaveServer.getServer_id());
        IWServer server = serverService.getServerById(leaveServer.getServer_id());

        if(server == null)
        {
            log.warn("Server is null");
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        else if(leaveServer.getUser().getId() == 0)
        {
            log.warn("User is null");
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        else
        {
            log.info("Found server: " + server.getName());

            if(server.getUsers().stream().filter(u->u.getId() == leaveServer.getUser().getId()).findAny().orElse(null) == null)
            {
                log.info("User is not in server");
                return new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY);
            }

            server.getUsers().remove(leaveServer.getUser());

            log.info("Removed user from server");

            if(server.getOwner().getId() == leaveServer.getUser().getId())
            {
                log.info("Owner is leaving");
                if(server.getUsers().size() > 1)
                {
                    log.info("User list is higher then 0");

                    User owner = server.getUsers().stream().filter(u-> u.getId() != leaveServer.getUser().getId()).findAny().orElse(null);
                    server.setOwner(owner);
                    log.info("Setting new owner to server");
                }
                else
                {
                    log.info("Deleting server");
                    serverService.deleteServer(server);
                }
            }
            else {
                log.info("Updating server");
                serverService.updateServer(server);
            }

            return new ResponseEntity(HttpStatus.OK);
        }
    }
}
