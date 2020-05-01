package userlogic.dal.context;

import org.hibernate.Session;
import org.hibernate.Transaction;
import userlogic.dal.hibernate.HibernateFactory;
import userlogic.interfaces.IUserContext;
import userlogic.models.User;

import javax.annotation.Nullable;
import javax.persistence.TypedQuery;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserHibernateContext implements IUserContext {
    private HibernateFactory hibernateFactory;

    public UserHibernateContext(@Nullable HibernateFactory hibernateFactory) {
        if (hibernateFactory == null) {
            this.hibernateFactory = HibernateFactory.getInstance();
        } else {
            this.hibernateFactory = hibernateFactory;
        }
    }

    @Override
    public boolean addUser(User user) {
        boolean result;

        Session session = hibernateFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.persist(user);
            transaction.commit();
            result = true;
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }

            result = false;
            Logger.getLogger(UserHibernateContext.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        } finally {
            session.close();
        }

        return result;
    }

    @Override
    public void updateDisplayName(String displayName) {
        //not implemented
    }

    @Override
    public void updateBio(String bio) {
        //not implemented
    }

    @Override
    public void setBio(String bio) {
        //not implemented
    }

    @Override
    public User getUserByEmail(String email) {
        User user;
        Session session = hibernateFactory.getSessionFactory().openSession();
        try {
            String hql = "SELECT c FROM User c WHERE c.email = :email";

            TypedQuery<User> typedQuery = session.createQuery(hql, User.class);
            typedQuery.setParameter("email", email);

            user = typedQuery.getSingleResult();
        } catch (Exception ex) {
            Logger.getLogger(UserHibernateContext.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            return null;
        } finally {
            session.close();
        }

        return user;
    }

    @Override
    public User getUserById(Integer id) {
        User user;

        Session session = hibernateFactory.getSessionFactory().openSession();
        try {
            user = session.find(User.class,id);
        } catch (Exception ex) {
            Logger.getLogger(UserHibernateContext.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            return null;
        } finally {
            session.close();
        }

        return user;
    }
}
