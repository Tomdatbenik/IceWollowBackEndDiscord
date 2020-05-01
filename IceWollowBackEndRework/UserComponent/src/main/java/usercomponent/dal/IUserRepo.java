package usercomponent.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import usercomponent.models.User;

public interface IUserRepo extends JpaRepository<User, String> {

    User getUserByEmail(String email);
}
