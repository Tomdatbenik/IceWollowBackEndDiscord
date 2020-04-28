package websocketserver.handlers;

import org.eclipse.jetty.websocket.api.Session;
import websocketserver.interfaces.IHandler;
import websocketserver.models.Message;

public class HelloWorldHandler implements IHandler<Message> {

    @Override
    public void Handle(Message messageObject, Session session) {
        System.out.println(session.getRemote() + " " + messageObject.getContent());
    }
}
