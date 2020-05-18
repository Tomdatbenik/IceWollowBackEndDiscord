package serverwebsocket.handlers;

import lombok.AllArgsConstructor;
import org.eclipse.jetty.websocket.api.Session;
import org.springframework.stereotype.Service;
import restmodule.models.User;
import restmodule.service.ServerService;
import restmodule.service.UserService;
import serverwebsocket.interfaces.IHandler;
import serverwebsocket.managers.ServerManager;
import serverwebsocket.messages.ChannelMessage;
import serverwebsocket.models.Client;

@Service("SubscribeChannelHandler")
@AllArgsConstructor
public class SubscribeChannelHandler implements IHandler<ChannelMessage> {

    private UserService userContainerLogic;
    private ServerService serverService;

    @Override
    public ChannelMessage getEmptyMessageObject() {
        return new ChannelMessage();
    }

    @Override
    public void Handle(ChannelMessage messageObject, Session session) {
        ServerManager serverManager = ServerManager.getInstance(serverService);
        User user = userContainerLogic.getUserById(Integer.parseInt(messageObject.getUser_id()));
        Client client = new Client(user, session);

        serverManager.subscribeClientToChannel(client, Integer.parseInt(messageObject.getChannel_id()));
    }
}