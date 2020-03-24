package user.interfaces;

//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import user.models.User;

@Repository
public interface IUserContainerRepo /*extends JpaRepository<User,String>*/ {
    User GetUserByEmail(String email);
    User GetUserById(Integer id);
    boolean AddUser(User user);
}
