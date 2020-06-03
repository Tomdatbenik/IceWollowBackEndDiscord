package restmodule.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import restmodule.dal.IUserRepo;
import restmodule.models.User;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Sql(scripts = "/tests.sql")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestPropertySource("/applicationtest.properties")
public class UserServiceTests {

    @Autowired
    private IUserRepo repo;

    @Autowired
    private UserService service;

    @Test
    @Transactional
    void addUserTest() {
//        User user = new User();
//        user.setId(2);
//        user.setEmail("Test2@test.com");
//        user.setDisplayName("Test add user");
//
//        boolean adduserRestult = service.addUser(user);
//        assertThat(adduserRestult).isEqualTo(true);
//
//        User resultUser = service.getUserByEmail("Test2@test.com");
//
//        assertThat(resultUser.getDisplayName()).isEqualTo("Test add user");
//        assertThat(resultUser.getEmail()).isEqualTo("Test2@test.com");
//        assertThat(resultUser.getId()).isEqualTo(2);
    }
}
