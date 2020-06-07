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
import serverwebsocket.messages.InitIdMessage;
import serverwebsocket.models.Client;

@Service("InitIdHandler")
@AllArgsConstructor
public class InitIdHandler implements IHandler<InitIdMessage> {

    private UserService userContainerLogic;
    private ServerService serverService;

    @Override
    public InitIdMessage getEmptyMessageObject() {
        return new InitIdMessage();
    }

    @Override
    public void Handle(InitIdMessage messageObject, Session session) {
        ServerManager serverManager = ServerManager.getInstance(serverService);
        User user = userContainerLogic.getUserById(Integer.parseInt(messageObject.getUser_id()));
        Client client = new Client(user, session);

        serverManager.connectPeerInitPeer(client, messageObject.getRtc_id());
    }
}