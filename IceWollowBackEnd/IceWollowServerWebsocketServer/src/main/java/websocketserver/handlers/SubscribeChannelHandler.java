package websocketserver.handlers;

import org.eclipse.jetty.websocket.api.Session;
import user.logic.UserContainerLogic;
import user.models.User;
import websocketserver.interfaces.IHandler;
import websocketserver.managers.ServerManager;
import websocketserver.messages.ChannelMessage;
import websocketserver.messages.ServerMessage;
import websocketserver.models.Client;

public class SubscribeChannelHandler implements IHandler<ChannelMessage> {

    @Override
    public ChannelMessage getEmptyMessageObject() {
        return new ChannelMessage();
    }

    @Override
    public void Handle(ChannelMessage messageObject, Session session) {
        ServerManager serverManager = ServerManager.getInstance();
        UserContainerLogic userContainerLogic = new UserContainerLogic();

        User user = userContainerLogic.getUserById(Integer.parseInt(messageObject.getUser_id()));
        Client client = new Client(user, session);

        serverManager.subscribeClientToChannel(client, Integer.parseInt(messageObject.getChannel_id()));
    }
}