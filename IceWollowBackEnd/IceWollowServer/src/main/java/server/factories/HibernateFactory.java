package server.factories;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import server.models.IWServer;

public class HibernateFactory {

    private static HibernateFactory instance;
    private static boolean test;

    public static HibernateFactory getInstance(boolean test) {
        if(instance == null)
        {
            instance = new HibernateFactory();
        }

        HibernateFactory.test = test;
        return instance;
    }

    public SessionFactory getSessionFactory() {
        Configuration configuration = getConfiguration();

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    private Configuration getConfiguration()
    {
        Configuration configuration = new Configuration();
        if(!HibernateFactory.test)
        {
            configuration.configure("hibernate.cfg.xml");
        }
        else
        {
            configuration.configure("hibernate.cfg.test.xml");
        }

        configuration.addAnnotatedClass(IWServer.class);

        return configuration;
    }
}
