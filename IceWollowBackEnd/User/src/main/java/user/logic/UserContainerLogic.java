package user.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import user.dal.repo.UserContainerRepo;
import user.factories.UserFactory;
import user.interfaces.IUserContainerRepo;
import user.models.User;

//@Service
public class UserContainerLogic {

    private UserFactory userFactory = UserFactory.getInstance();
    private IUserContainerRepo repo;

    //@Autowired
    public UserContainerLogic(/*IUserContainerRepo repo*/) {
        userFactory = UserFactory.getInstance();
        repo = userFactory.GetUserContainerRepo();
//        this.repo = repo;
    }

    public User getUserByEmail(String email) {
//        String test = repo.getOne("1").getDisplayName();
        return repo.GetUserByEmail(email);
    }

    public boolean addUser(User user) {
        if (repo.GetUserByEmail(user.getEmail()) == null) {
            return repo.AddUser(user);
        } else {
            return false;
        }
    }
}
