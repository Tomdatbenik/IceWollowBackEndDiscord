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

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestPropertySource("/applicationtest.properties")
public class UserRepoTest {

    @Autowired
    private IUserRepo repo;

    @Test
    @Transactional
    void getServerWithSearchCode() {
        User user = repo.getUserByEmail("test@test.com");

        assertThat(user.getId()).isEqualTo(1);
    }
}
