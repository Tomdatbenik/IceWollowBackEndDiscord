package user.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import user.models.User;

@Controller
@RequestMapping("/user")
public class UserController {

    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    public String randomNames() {
        return "test";
    }

    @GetMapping(value = "/getById/{userId}")
    public User GetUserById(@PathVariable("userId") int userId)
    {
        return new User();
    }

    @GetMapping(value = "/getById/{email}")
    public boolean CheckUserByEmail(@PathVariable("email") String email)
    {
        return false;
    }

}
