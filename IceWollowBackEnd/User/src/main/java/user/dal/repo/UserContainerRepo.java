package user.dal.repo;

import user.interfaces.IUserContainerRepo;
import user.interfaces.IUserContext;
import user.models.User;

public class UserContainerRepo implements IUserContainerRepo{

    private IUserContext context;

    public UserContainerRepo(IUserContext context) {
        this.context = context;
    }


    public User getUserByEmail(String email) {
        return context.getUserByEmail(email);
    }


    public User getUserById(Integer id) {
        return context.getUserById(id);
    }

    @Override
    public boolean addUser(User user) {
        return context.addUser(user);
    }

}
