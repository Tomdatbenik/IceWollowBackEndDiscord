package servercomponent;

import org.hibernate.Session;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import server.dal.hibernate.HibernateFactory;
import server.factories.ServerFactory;
import server.interfaces.IServerContainerRepo;
import server.logic.ServerContainerLogic;
import server.models.IWServer;
import user.factories.UserFactory;
import user.interfaces.IUserContainerRepo;
import user.models.User;

import java.util.List;

public class ServerContainerLogicTest {

    private IServerContainerRepo repo;
    private ServerContainerLogic serverContainerLogic = new ServerContainerLogic();
    private HibernateFactory hibernateFactory;
    IUserContainerRepo userContainerRepo = UserFactory.getInstance().getTestUserContainerRepo();

    @Before
    public void setUp() {
        hibernateFactory = HibernateFactory.getTestInstance(true);

        repo = ServerFactory.getInstance().getMemoryTestServerContainerRepo();
    }

    @Test
    public void testAddServer() {
        User user = new User();
        user.setDisplayName("Test");
        user.setEmail("TestEmail@gmail.com");

        userContainerRepo.addUser(user);
        user = userContainerRepo.getUserByEmail(user.getEmail());

        IWServer server = new IWServer();
        server.setName("Test server");
        server.setOwner(user);

        IWServer resultServer = new IWServer();

        Session session = hibernateFactory.getSessionFactory().openSession();
        List<IWServer> servers = session.createQuery("SELECT a FROM IWServer a", IWServer.class).getResultList();
        System.out.println(servers);
        session.close();

        Assert.assertEquals(server.getName(), servers.get(0).getName());
    }
}
