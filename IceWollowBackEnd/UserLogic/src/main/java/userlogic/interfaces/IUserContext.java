package userlogic.interfaces;

import userlogic.models.User;

public interface IUserContext {
    boolean addUser(User user);
    void updateDisplayName(String displayName);
    void updateBio(String bio);
    void setBio(String bio);
    User getUserByEmail(String email);
    User getUserById(Integer id);

}
