package user.controllers;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import user.logic.UserContainerLogic;
import user.models.User;

@RestController
@RequestMapping(path= "user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    //@Autowired
    private UserContainerLogic userContainerLogic = new UserContainerLogic();

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
        return null;
    }

    @GetMapping(value = "getUserByEmail")
    public User getUserByEmail(String email)
    {
        return userContainerLogic.getUserByEmail(email);
    }

    @PostMapping(value = "/add")
    public boolean addUser(@RequestBody Object user)
    {
        return userContainerLogic.addUser((User)user);
    }

}
