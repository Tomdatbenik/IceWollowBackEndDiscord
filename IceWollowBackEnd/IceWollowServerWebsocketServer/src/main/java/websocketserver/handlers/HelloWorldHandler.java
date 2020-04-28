package websocketserver.handlers;

import org.eclipse.jetty.websocket.api.Session;
import websocketserver.interfaces.IHandler;
import websocketserver.messages.BaseMessage;

public class HelloWorldHandler implements IHandler<BaseMessage> {

    @Override
    public BaseMessage getEmptyMessageObject() {
        return new BaseMessage();
    }

    @Override
    public void Handle(BaseMessage messageObject, Session session) {
        System.out.println(session.getRemote() + " " + messageObject.getContent());
    }
}
