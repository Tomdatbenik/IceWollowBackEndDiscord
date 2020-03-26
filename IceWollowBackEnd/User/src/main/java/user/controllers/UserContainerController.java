package user.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import user.logic.UserContainerLogic;
import user.models.User;
import user.models.datamodels.UserDataModel;

@RestController
@RequestMapping(path= "user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserContainerController {

    //@Autowired
    private UserContainerLogic userContainerLogic = new UserContainerLogic();

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
    public boolean addUser(@RequestBody UserDataModel userdm)
    {
        User user = new User(userdm.getDisplayName(), userdm.getEmail());

        return userContainerLogic.addUser(user);
    }

}
