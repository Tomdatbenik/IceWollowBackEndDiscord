package usercomponent.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import usercomponent.dal.IUserRepo;
import usercomponent.models.User;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    @Autowired
    private final IUserRepo repo;

    public User getUserByEmail(String email) {
        return repo.getUserByEmail(email);
    }

    public User getUserById(int id) {

        List<User> userList = repo.findAll();

        return repo.findById(id).orElse(null );
    }

    public boolean addUser(User user) {
        if (repo.getUserByEmail(user.getEmail()) == null) {
            repo.save(user);
            return true;
        } else {
            return false;
        }
    }
}
