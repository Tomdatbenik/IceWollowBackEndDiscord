package user.logic;

import user.factories.UserFactory;
import user.interfaces.IUserContainerRepo;
import user.models.User;

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

    public boolean addUser(User user) {
        if (repo.getUserByEmail(user.getEmail()) == null) {
            return repo.addUser(user);
        } else {
            return false;
        }
    }
}
