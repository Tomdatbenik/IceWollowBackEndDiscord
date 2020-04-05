package server.dal.context;

import org.hibernate.Session;
import org.hibernate.Transaction;
import server.interfaces.IServerContext;
import server.models.IWServer;
import server.dal.hibernate.HibernateFactory;
import user.models.User;

import javax.annotation.Nullable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerHibernateContext implements IServerContext {
    private HibernateFactory hibernateFactory;

    public ServerHibernateContext(@Nullable HibernateFactory hibernateFactory) {
        if (hibernateFactory == null) {
            this.hibernateFactory = HibernateFactory.getInstance();
        } else {
            this.hibernateFactory = hibernateFactory;
        }
    }

    @Override
    public boolean updateServer(IWServer server) {
        return false;
    }

    @Override
    public boolean addServer(IWServer server) {
        boolean result;

        Session session = hibernateFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.persist(server);
            transaction.commit();
            result = true;
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }

            result = false;
            Logger.getLogger(ServerHibernateContext.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        } finally {
            session.close();
        }

        return result;
    }

    @Override
    public List<IWServer> getAllServersByUser(User user) {
        return null;
    }

    @Override
    public IWServer getServerById(int id) {
        return null;
    }
}
