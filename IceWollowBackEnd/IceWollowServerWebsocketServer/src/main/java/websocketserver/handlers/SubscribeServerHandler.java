package websocketserver.handlers;

import org.eclipse.jetty.websocket.api.Session;
import user.logic.UserContainerLogic;
import user.models.User;
import websocketserver.interfaces.IHandler;
import websocketserver.managers.ServerManager;
import websocketserver.messages.ServerMessage;
import websocketserver.models.Client;

public class SubscribeServerHandler implements IHandler<ServerMessage> {

    @Override
    public ServerMessage getEmptyMessageObject() {
        return new ServerMessage();
    }

    @Override
    public void Handle(ServerMessage messageObject, Session session) {

        ServerManager serverManager = ServerManager.getInstance();
        UserContainerLogic userContainerLogic = new UserContainerLogic();

        User user = userContainerLogic.getUserById(Integer.parseInt(messageObject.getUser_id()));
        Client client = new Client(user, session);

        serverManager.subscribeClientToServer(client, Integer.parseInt(messageObject.getServer_id()));
    }
}