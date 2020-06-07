package serverwebsocket.handlers;

import lombok.AllArgsConstructor;
import org.eclipse.jetty.websocket.api.Session;
import org.springframework.stereotype.Service;
import restmodule.models.User;
import restmodule.service.ServerService;
import restmodule.service.UserService;
import serverwebsocket.interfaces.IHandler;
import serverwebsocket.managers.ServerManager;
import serverwebsocket.messages.rtcIdMessage;
import serverwebsocket.models.Client;

@Service("rtcIdHandler")
@AllArgsConstructor
public class rtcIdHandler implements IHandler<rtcIdMessage> {

    private UserService userContainerLogic;
    private ServerService serverService;

    @Override
    public rtcIdMessage getEmptyMessageObject() {
        return new rtcIdMessage();
    }

    @Override
    public void Handle(rtcIdMessage messageObject, Session session) {
        ServerManager serverManager = ServerManager.getInstance(serverService);
        User user = userContainerLogic.getUserById(Integer.parseInt(messageObject.getUser_id()));
        Client client = new Client(user, session);

        serverManager.connectPeerInitPeer(client, messageObject.getRtc_id());
    }
}