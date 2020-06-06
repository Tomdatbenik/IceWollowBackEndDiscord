package restmodule.repo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import restmodule.dal.IServerRepo;
import restmodule.models.IWServer;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Sql(scripts = "/test/serverTest.sql")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestPropertySource("/applicationtest.properties")
public class ServerRepoTest {

    @Autowired
    private IServerRepo repo;

    @Test
    @Transactional
    void getServerWithSearchCode() {
        IWServer server = repo.getServerByCode("561");

        assertThat(server.getId()).isEqualTo(3);
    }
}
