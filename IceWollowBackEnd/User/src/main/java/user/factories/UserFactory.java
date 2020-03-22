package user.factories;

import user.dal.repo.UserContainerRepo;
import user.dal.repo.UserRepo;
import user.interfaces.IUserContainerRepo;
import user.interfaces.IUserRepo;

public class UserFactory {

    private static UserFactory instance;

    public static UserFactory getInstance() {
        if(instance == null)
        {
            instance = new UserFactory();
        }

        return instance;
    }

    public IUserContainerRepo GetUserContainerRepo(){
        return new UserContainerRepo();
    }

    public IUserRepo GetUserRepo(){
        return new UserRepo();
    }


}
