package user.interfaces;

//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import user.models.User;

@Repository
public interface IUserContainerRepo /*extends JpaRepository<User,String>*/ {
    User getUserByEmail(String email);
    User getUserById(Integer id);
    boolean addUser(User user);
}
