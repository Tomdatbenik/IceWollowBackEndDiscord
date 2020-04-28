package websocketserver.interfaces;

import org.eclipse.jetty.websocket.api.Session;

public interface IHandler<T> {
    //TODO create client and give client to handler
    void Handle(T messageObject, Session session);
}
