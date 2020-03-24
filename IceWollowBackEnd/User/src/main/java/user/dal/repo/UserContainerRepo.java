package user.dal.repo;

import user.interfaces.IUserContainerRepo;
import user.interfaces.IUserContext;
import user.models.User;

public class UserContainerRepo implements IUserContainerRepo{

    private IUserContext context;

    public UserContainerRepo(IUserContext context) {
        this.context = context;
    }


    public User GetUserByEmail(String email) {
        return context.GetUserByEmail(email);
    }


    public User GetUserById(Integer id) {
        return null;
    }

    @Override
    public boolean AddUser(User user) {
        return context.AddUser(user);
    }

}
