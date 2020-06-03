package restmodule.controllers;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import restmodule.models.User;
import restmodule.service.UserService;

@RestController
@AllArgsConstructor
@RequestMapping(path= "user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    @Autowired
    private UserService userService;

    @CrossOrigin(origins = {"*"})
    @GetMapping(value = "getById/{userId}")
    public User getUserById(@PathVariable("userId") int userId)
    {
        return userService.getUserById(userId);
    }

    @CrossOrigin(origins = {"*"})
    @GetMapping(value = "getUserByEmail")
    public User getUserByEmail(String email)
    {
        return userService.getUserByEmail(email);
    }

    @CrossOrigin(origins = {"*"})
    @PostMapping(value = "/add")
    public boolean addUser(@RequestBody String user)
    {
        Gson gson = new Gson();
        return userService.addUser(gson.fromJson(user,User.class));
    }

}