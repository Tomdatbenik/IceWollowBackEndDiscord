package server.factories;

import server.dal.context.ServerHibernateContext;
import server.dal.hibernate.HibernateFactory;
import server.dal.repo.ServerContainerRepo;
import server.dal.repo.ServerRepo;
import server.interfaces.IServerContainerRepo;
import server.interfaces.IServerRepo;

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
