package restmodule.repo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import restmodule.dal.IUserRepo;
import restmodule.models.User;

import javax.transaction.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource("/applicationtest.properties")
@Sql(scripts = "/test/userTest.sql")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserRepoTest {

    @Autowired
    private IUserRepo repo;

    @Test
    @Transactional
    void getUserByEmail() {
        User user = repo.getUserByEmail("testUser@test.com");

        List<User> users = repo.findAll();

        assertThat(user).isNotEqualTo(null);
        assertThat(user.getId()).isEqualTo(10);
    }
}
