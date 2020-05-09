package usercomponent.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import usercomponent.models.User;

public interface IUserRepo extends JpaRepository<User, Integer> {

    User getUserByEmail(String email);
}
