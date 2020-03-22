package user.interfaces;

import user.models.User;

public interface IUserContainerRepo {
    User GetUserByEmail(String email);
    User GetUserById(Integer id);
}
