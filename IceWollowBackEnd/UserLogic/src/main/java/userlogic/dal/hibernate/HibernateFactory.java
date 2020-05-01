package userlogic.dal.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import userlogic.models.User;

public class HibernateFactory {

    private static HibernateFactory instance;
    private static HibernateFactory testInstance;
    private boolean test;
    private boolean memoryTest;

    public static HibernateFactory getInstance() {
        if(instance == null)
        {
            instance = new HibernateFactory();
        }

        return instance;
    }


    public static HibernateFactory getTestInstance(boolean memory) {
        if(testInstance == null)
        {
            testInstance = new HibernateFactory();
        }

        testInstance.test = true;
        testInstance.memoryTest = memory;
        return testInstance;
    }

    public SessionFactory getSessionFactory() {
        Configuration configuration = getConfiguration();

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    private Configuration getConfiguration()
    {
        Configuration configuration = new Configuration();
        if(!this.test)
        {
            configuration.configure("hibernate.cfg.xml");
        }
        else
        {
            if(this.memoryTest)
            {
                configuration.configure("hibernate.cfg.test.xml");
            }
            else
            {
                configuration.configure("hibernate.cfg.integration.test.xml");
            }
        }


        configuration.addAnnotatedClass(User.class);

        return configuration;
    }
}
