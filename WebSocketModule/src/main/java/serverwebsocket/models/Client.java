package serverwebsocket.models;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restmodule.models.User;
import serverwebsocket.messages.BaseMessage;

import java.io.IOException;

public class Client {
    private Session session;
    private User user;

    public User getUser() {
        return user;
    }

    public Session getSession() {
        return session;
    }

    public Client(User user, Session session) {
        this.session = session;
        this.user = user;
    }

    private final Logger logger = LoggerFactory.getLogger(Client.class);
    private Gson gson = new Gson();

    public void sendMessage(BaseMessage message) {
        try {
            logger.info("Sending Message to: " + user.getDisplayName());
            session.getRemote().sendString(gson.toJson(message));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
