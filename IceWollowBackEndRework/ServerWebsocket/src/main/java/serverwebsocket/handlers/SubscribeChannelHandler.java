package serverwebsocket.handlers;

import lombok.AllArgsConstructor;
import org.eclipse.jetty.websocket.api.Session;
import org.springframework.stereotype.Component;
import servercomponent.service.ServerService;
import serverwebsocket.interfaces.IHandler;
import serverwebsocket.managers.ServerManager;
import serverwebsocket.messages.ChannelMessage;
import serverwebsocket.models.Client;
import usercomponent.models.User;
import usercomponent.service.UserService;


@Component
@AllArgsConstructor
public class SubscribeChannelHandler implements IHandler<ChannelMessage> {

    private final UserService userContainerLogic;
    private final ServerService serverService;

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