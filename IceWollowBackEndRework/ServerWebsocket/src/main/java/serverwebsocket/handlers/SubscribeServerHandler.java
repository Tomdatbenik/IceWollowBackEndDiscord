package serverwebsocket.handlers;

import lombok.AllArgsConstructor;
import org.eclipse.jetty.websocket.api.Session;
import org.springframework.stereotype.Component;
import servercomponent.service.ServerService;
import serverwebsocket.interfaces.IHandler;
import serverwebsocket.managers.ServerManager;
import serverwebsocket.messages.ServerMessage;
import serverwebsocket.models.Client;
import usercomponent.models.User;
import usercomponent.service.UserService;

@Component
@AllArgsConstructor
public class SubscribeServerHandler implements IHandler<ServerMessage> {

    private UserService userContainerLogic;
    private final ServerService serverService;

    @Override
    public ServerMessage getEmptyMessageObject() {
        return new ServerMessage();
    }

    @Override
    public void Handle(ServerMessage messageObject, Session session) {

        ServerManager serverManager = ServerManager.getInstance(serverService);

        User user = userContainerLogic.getUserById(Integer.parseInt(messageObject.getUser_id()));
        Client client = new Client(user, session);

        serverManager.subscribeClientToServer(client, Integer.parseInt(messageObject.getServer_id()));
    }
}