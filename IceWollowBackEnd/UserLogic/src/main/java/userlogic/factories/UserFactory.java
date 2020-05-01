package userlogic.factories;

import userlogic.dal.context.UserHibernateContext;
import userlogic.dal.hibernate.HibernateFactory;
import userlogic.dal.repo.UserContainerRepo;
import userlogic.dal.repo.UserRepo;
import userlogic.interfaces.IUserContainerRepo;
import userlogic.interfaces.IUserRepo;

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
