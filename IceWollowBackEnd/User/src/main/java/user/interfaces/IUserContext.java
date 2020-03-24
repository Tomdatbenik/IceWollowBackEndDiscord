package user.interfaces;

import user.factories.HibernateFactory;
import user.models.User;

public interface IUserContext {
    boolean AddUser(User user);
    void UpdateDisplayName(String DisplayName);
    void UpdateBio(String bio);
    void SetBio(String bio);
    User GetUserByEmail(String email);
    User GetUserById(Integer id);

}
