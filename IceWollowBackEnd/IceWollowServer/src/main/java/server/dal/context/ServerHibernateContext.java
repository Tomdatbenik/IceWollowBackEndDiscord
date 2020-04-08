package server.dal.context;

import org.hibernate.Session;
import org.hibernate.Transaction;
import server.interfaces.IServerContext;
import server.models.IWServer;
import server.dal.hibernate.HibernateFactory;
import user.dal.context.UserHibernateContext;
import user.models.User;

import javax.annotation.Nullable;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
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
        Session session = hibernateFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.merge(server);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            Logger.getLogger(ServerHibernateContext.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public IWServer addServer(IWServer server) {
        Session session = hibernateFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.persist(server);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            Logger.getLogger(ServerHibernateContext.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        } finally {
            session.close();
        }

        return server;
    }

    @Override
    public List<IWServer> getAllServersByUser(User user) {
        List<IWServer> servers;
        Session session = hibernateFactory.getSessionFactory().openSession();
        try {
            String hql = "SELECT s FROM IWServer s";

            TypedQuery<IWServer> typedQuery = session.createQuery(hql, IWServer.class);

            servers = typedQuery.getResultList();
        } catch (Exception ex) {
            Logger.getLogger(UserHibernateContext.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            return null;
        } finally {
            session.close();
        }

        List<IWServer> resultList = new ArrayList<>();

        servers.stream().forEach(s -> s.getUsers().stream().forEach(u -> {
                    if (user.getId() == u.getId()) {
                        resultList.add(s);
                    }
                }
        ));

        return resultList;
    }

    @Override
    public IWServer getServerById(int id) {
        Session session = hibernateFactory.getSessionFactory().openSession();

        IWServer server = new IWServer();

        try {
            server = session.find(IWServer.class, id);
        } catch (Exception ex) {
            Logger.getLogger(ServerHibernateContext.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        } finally {
            session.close();
        }

        return server;
    }
}
