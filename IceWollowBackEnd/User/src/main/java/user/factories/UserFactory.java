package user.factories;

import user.dal.context.UserHibernateContext;
import user.dal.hibernate.HibernateFactory;
import user.dal.repo.UserContainerRepo;
import user.dal.repo.UserRepo;
import user.interfaces.IUserContainerRepo;
import user.interfaces.IUserRepo;

public class UserFactory {

    private static UserFactory instance;
    private static boolean Test;
    private static boolean memoryTest;

    public static UserFactory getInstance() {
        if(instance == null)
        {
            instance = new UserFactory();
        }

        return instance;
    }

    public IUserContainerRepo getUserContainerRepo(){
        return new UserContainerRepo(new UserHibernateContext(null));
    }

    public IUserContainerRepo getTestUserContainerRepo(){
        return new UserContainerRepo(new UserHibernateContext(HibernateFactory.getTestInstance(true)));
    }

    public IUserContainerRepo getIntegrationTestServerContainerRepo(){
        return new UserContainerRepo(new UserHibernateContext(HibernateFactory.getTestInstance(false)));
    }

    public IUserRepo getUserRepo(){
        return new UserRepo();
    }
}
