package serverwebsocket.manager;

import lombok.AllArgsConstructor;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import restmodule.dal.IUserRepo;
import restmodule.models.IWServer;
import restmodule.models.User;
import restmodule.models.VoiceChannel;
import restmodule.service.ServerService;
import serverwebsocket.managers.ServerManager;
import serverwebsocket.models.Client;
import serverwebsocket.models.ServerObserver;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource("/applicationtest.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ServerManagerTest {
    @Autowired
    private ServerService serverService;
    @Autowired
    private IUserRepo repo;

    private final void SetupManagerWithData()
    {
        User user = new User(1,"Test", "test@test.com");
        ServerManager serverManager = ServerManager.getInstance(serverService);

        Client client = new Client(user,null);

        IWServer server = new IWServer();
        server.setId(1);
        server.setName("Test");

        VoiceChannel channel = new VoiceChannel();
        channel.setId(1);
        channel.setName("Test channel");

        server.getVoiceChannels().add(channel);

        serverManager.getActiveServers().add(server);
        serverManager.getObservers().add(new ServerObserver(client));

        serverManager.subscribeClientToChannel(client,1);
    }

//    @Test
//    @Transactional
//    void subscribeClientToServerSuccesTest() {
//        User user = new User(1,"test", "test@test.com");
//        ServerManager serverManager = ServerManager.getInstance(serverService);
//        Client client = new Client(user,null);
//
//        serverManager.subscribeClientToServer(client,1);
//
//        assertThat(serverManager.getActiveServers().size()).isEqualTo(1);
//    }

    @Test
    @Transactional
    void subscribeClientToServerNoClientTest() {
        Client client = null;
        ServerManager serverManager = ServerManager.getInstance(serverService);
        serverManager.subscribeClientToServer(client,1);

        assertThat(serverManager.getActiveServers().size()).isEqualTo(0);
    }

    @Test
    void subscribeClientToChannelTest() {
        User user = new User(1,"Test", "test@test.com");
        ServerManager serverManager = ServerManager.getInstance(serverService);

        Client client = new Client(user,null);

        SetupManagerWithData();

        serverManager.subscribeClientToChannel(client,1);

        assertThat(serverManager.getObservers().size()).isEqualTo(1);
    }

    @Test
    @Transactional
    void subscribeClientToChannelNoChannelTest() {
        Client client = null;
        ServerManager serverManager = ServerManager.getInstance(serverService);

        serverManager.subscribeClientToChannel(client,1);

        assertThat(serverManager.getObservers().size()).isEqualTo(1);
    }

//    @Test
//    @Transactional
//    void unsubscribeClientToServerClientNoClientTest() {
//        User user = repo.getOne(1);
//        ServerManager serverManager = ServerManager.getInstance(serverService);
//
//        Client client = new Client(user,null);
//        serverManager.subscribeClientToChannel(client,1);
//
//        assertThat(serverManager.getObservers().size()).isEqualTo(1);
//
//        serverManager.unsubscribeClientToChannel(client,1);
//
//        assertThat(serverManager.getObservers().size()).isEqualTo(0);
//    }

}

