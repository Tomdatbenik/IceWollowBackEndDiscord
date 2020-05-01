package userlogic.dal.repo;

import userlogic.interfaces.IUserContainerRepo;
import userlogic.interfaces.IUserContext;
import userlogic.models.User;

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
