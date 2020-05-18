package restmodule.controllers;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import restmodule.models.User;
import restmodule.service.UserService;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping(path= "user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "test", consumes = "application/json", produces = "application/json")
    public String testPost() {
        return "test";
    }

    @GetMapping(value = "test")
    public String testGet() {
        Gson gson = new Gson();

        return gson.toJson(new User());
    }

    @GetMapping(value = "getById/{userId}")
    public User getUserById(@PathVariable("userId") int userId)
    {
        return userService.getUserById(userId);
    }

    @GetMapping(value = "getUserByEmail")
    public User getUserByEmail(String email)
    {
        return userService.getUserByEmail(email);
    }

    @PostMapping(value = "/add")
    public boolean addUser(@RequestBody String user)
    {
        Gson gson = new Gson();
        return userService.addUser(gson.fromJson(user,User.class));
    }

}