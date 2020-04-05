package server.dal.hibernate;

import chat.models.Chat;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import server.models.Channel;
import server.models.IWServer;
import server.models.TextChannel;
import server.models.VoiceChannel;
import user.models.User;

public class HibernateFactory {

    private static HibernateFactory instance;
    private static HibernateFactory Testinstance;
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
        if(Testinstance == null)
        {
            Testinstance = new HibernateFactory();
        }
        Testinstance.test=true;
        Testinstance.memoryTest = memory;
        return Testinstance;
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

        configuration.addAnnotatedClass(IWServer.class);
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Channel.class);
        configuration.addAnnotatedClass(VoiceChannel.class);
        configuration.addAnnotatedClass(TextChannel.class);
        configuration.addAnnotatedClass(Chat.class);

        return configuration;
    }
}
