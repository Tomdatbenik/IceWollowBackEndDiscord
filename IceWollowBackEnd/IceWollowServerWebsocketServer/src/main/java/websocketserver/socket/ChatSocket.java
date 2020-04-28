package websocketserver.socket;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import websocketserver.util.ChatBeanUtil;


@WebSocket
public class ChatSocket {

    @OnWebSocketConnect
    public void onConnect(Session session) {
        //ChatBeanUtil.getStockpileService().addClient(session);
    }

    @OnWebSocketClose
    public void onClose(Session session, int _closeCode, String _closeReason) {
        //ChatBeanUtil.getStockpileService().removeClient(session);
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message)
    {
        //ChatBeanUtil.getStockpileService().handleMessage(message, session);
    }
}
