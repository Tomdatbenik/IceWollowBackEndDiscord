package serverlogic.factories;

import serverlogic.dal.context.ServerHibernateContext;
import serverlogic.dal.hibernate.HibernateFactory;
import serverlogic.dal.repo.ServerContainerRepo;
import serverlogic.dal.repo.ServerRepo;
import serverlogic.interfaces.IServerContainerRepo;
import serverlogic.interfaces.IServerRepo;

public class ServerFactory {
    private static ServerFactory instance;

    public static ServerFactory getInstance() {
        if(instance == null)
        {
            instance = new ServerFactory();
        }

        return instance;
    }

    public IServerContainerRepo getServerContainerRepo(){
        return new ServerContainerRepo(new ServerHibernateContext(null));
    }

    public IServerContainerRepo getMemoryTestServerContainerRepo(){
        return new ServerContainerRepo(new ServerHibernateContext(HibernateFactory.getTestInstance(true)));
    }

    public IServerContainerRepo getIntegrationTestServerContainerRepo(){
        return new ServerContainerRepo(new ServerHibernateContext(HibernateFactory.getTestInstance(false)));
    }

    public IServerRepo getServerRepo(){
        return new ServerRepo(new ServerHibernateContext(null));
    }
}
