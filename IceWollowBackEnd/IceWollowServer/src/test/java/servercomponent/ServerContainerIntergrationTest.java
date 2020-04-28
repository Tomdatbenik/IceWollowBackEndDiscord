package servercomponent;

import org.hibernate.Session;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import server.dal.hibernate.HibernateFactory;
import server.factories.ServerFactory;
import server.interfaces.IServerContainerRepo;
import server.models.IWServer;
import user.factories.UserFactory;
import user.interfaces.IUserContainerRepo;
import user.models.User;

import java.util.List;

public class ServerContainerIntergrationTest {

    private IServerContainerRepo repo;
    private HibernateFactory hibernateFactory;
    IUserContainerRepo userContainerRepo = UserFactory.getInstance().getIntegrationTestServerContainerRepo();

    @Before
    public void setUp() {
        hibernateFactory = HibernateFactory.getTestInstance(true);

        Session session = hibernateFactory.getSessionFactory().openSession();
        session.createQuery("DELETE FROM IWServer").executeUpdate();
        session.createQuery("DELETE FROM User").executeUpdate();
        session.close();

        repo = ServerFactory.getInstance().getIntegrationTestServerContainerRepo();
    }

    @Test
    public void testAddServer() {
        User user = new User();
        user.setDisplayName("Test");
        user.setEmail("TestEmail@gmail.com");

        if(userContainerRepo.addUser(user))
        {
            user = userContainerRepo.getUserByEmail(user.getEmail());

            IWServer server = new IWServer();
            server.setName("Test server");
            server.setOwner(user);

            server = repo.addServer(server);

            IWServer resultServer = new IWServer();

            Session session = hibernateFactory.getSessionFactory().openSession();
            List<IWServer> servers = session.createQuery("SELECT a FROM IWServer a", IWServer.class).getResultList();
            System.out.println(servers);
            session.close();

            Assert.assertEquals(server.getName(), servers.get(0).getName());
        }
    }
}
