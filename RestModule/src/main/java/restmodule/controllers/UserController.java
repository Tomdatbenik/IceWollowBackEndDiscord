package restmodule.controllers;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restmodule.models.User;
import restmodule.service.UserService;

import javax.net.ssl.HttpsURLConnection;

@RestController
@AllArgsConstructor
@RequestMapping(path = "user", produces = MediaType.APPLICATION_JSON_VALUE)
@Log4j2
public class UserController {

    @Autowired
    private UserService userService;

    @CrossOrigin(origins = {"*"})
    @GetMapping(value = "getUserByEmail")
    public ResponseEntity getUserByEmail(String email) {
        if(email != null && email != "")
        {
            log.info("Getting user with email: " + email);
            return new ResponseEntity(userService.getUserByEmail(email), HttpStatus.OK);
        }
        else
        {
            log.info("Email not set");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin(origins = {"*"})
    @PostMapping()
    public ResponseEntity addUser(@RequestBody User user) {

        if(user!=null)
        {
            log.info("Adding user with email: " + user.getEmail());
            return new ResponseEntity<>(userService.addUser(user), HttpStatus.OK);
        }
        else
        {
            log.info("User is null");
            return new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

}