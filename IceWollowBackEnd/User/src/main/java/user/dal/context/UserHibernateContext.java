package user.dal.context;

import org.hibernate.Session;
import org.hibernate.Transaction;
import user.factories.HibernateFactory;
import user.interfaces.IUserContext;
import user.models.User;

public class UserHibernateContext implements IUserContext {
    private HibernateFactory hibernateFactory = HibernateFactory.getInstance();
    private Session session;
    private Transaction transaction;


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

        try {
            session = hibernateFactory.getSessionFactory().openSession();
            user = session.find(User.class, email);
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
