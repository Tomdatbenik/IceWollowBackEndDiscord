package websocketserver.service;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import org.springframework.stereotype.Service;
import settlementcomponent.Application;
import javax.annotation.PostConstruct;
import javax.persistence.ManyToOne;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Service("chatSvc")
public class ChatService {

    private ScheduledExecutorService scheduledClientManagerExecutor;
    private Gson gson = new Gson();

    public void addClient(Session session) {

    }

    public void removeClient(Session session) {

    }

    @PostConstruct
    private void init() {
        Application.setUpProperties();

        scheduledClientManagerExecutor = Executors.newScheduledThreadPool(20);
    }

    //TODO create helloworld handler from message
    public void handleMessage(String sMessage, Session session) {

    }
}
