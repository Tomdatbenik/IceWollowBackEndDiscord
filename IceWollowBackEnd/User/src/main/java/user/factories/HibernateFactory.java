package user.factories;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import user.models.User;

public class HibernateFactory {

    private static HibernateFactory instance;

    public static HibernateFactory getInstance() {
        if(instance == null)
        {
            instance = new HibernateFactory();
        }

        return instance;
    }

    public SessionFactory getSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");

        configuration.addAnnotatedClass(User.class);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        return configuration.buildSessionFactory(serviceRegistry);
    }
}
