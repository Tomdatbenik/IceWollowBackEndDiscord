package user.dal.context;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import user.factories.HibernateFactory;
import user.interfaces.IUserContext;
import user.models.User;

import javax.annotation.Nullable;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class UserHibernateContext implements IUserContext {
    private HibernateFactory hibernateFactory;
    private SessionFactory sessionFactory;

    public UserHibernateContext( @Nullable HibernateFactory hibernateFactory) {
        if(hibernateFactory == null)
        {
            this.hibernateFactory = HibernateFactory.getInstance(false);
        }
        else
        {
            this.hibernateFactory = hibernateFactory;
        }

        this.sessionFactory = this.hibernateFactory.getSessionFactory();
    }

    @Override
    public boolean AddUser(User user) {
        boolean result = false;

        Session session = hibernateFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try
        {
            session.persist(user);
            transaction.commit();
            result = true;
        }
        catch (Exception ex)
        {
            if (transaction != null)
            {
                transaction.rollback();
            }

            result = false;
            ex.printStackTrace();
        } finally
        {
            session.close();
        }

        return result;
    }

    @Override
    public void UpdateDisplayName(String DisplayName) {

    }

    @Override
    public void UpdateBio(String bio) {

    }

    @Override
    public void SetBio(String bio) {

    }

    @Override
    public User GetUserByEmail(String email) {
        User user;
        Session session = hibernateFactory.getSessionFactory().openSession();
        try {
            String hql = "SELECT c FROM User c WHERE c.email = :email";

            TypedQuery<User> typedQuery = session.createQuery(hql, User.class);
            typedQuery.setParameter("email", email);

            user = typedQuery.getSingleResult();
        } catch (Exception ex) {
            return null;
        } finally {
            session.close();
        }

        return user;
    }

    @Override
    public User GetUserById(Integer id) {
        return null;
    }
}
