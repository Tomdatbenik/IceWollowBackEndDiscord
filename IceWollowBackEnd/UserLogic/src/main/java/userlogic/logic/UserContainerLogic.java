package userlogic.logic;

import userlogic.factories.UserFactory;
import userlogic.interfaces.IUserContainerRepo;
import userlogic.models.User;

//@Service
public class UserContainerLogic {

    private UserFactory userFactory;
    private IUserContainerRepo repo;


    //@Autowired
    public UserContainerLogic(/*IUserContainerRepo repo*/) {
        userFactory = UserFactory.getInstance();
        repo = userFactory.getUserContainerRepo();
//        this.repo = repo;
    }

    public User getUserByEmail(String email) {
//        String test = repo.getOne("1").getDisplayName();
        return repo.getUserByEmail(email);
    }

    public User getUserById(int id) {
//        String test = repo.getOne("1").getDisplayName();
        return repo.getUserById(id);
    }

    public boolean addUser(User user) {
        if (repo.getUserByEmail(user.getEmail()) == null) {
            return repo.addUser(user);
        } else {
            return false;
        }
    }
}
