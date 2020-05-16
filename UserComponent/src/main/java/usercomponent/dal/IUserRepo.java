package usercomponent.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import usercomponent.models.User;

@Repository
public interface IUserRepo extends JpaRepository<User, Integer> {

    User getUserByEmail(String email);
}
