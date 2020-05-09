package serverwebsocket.socket;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import serverwebsocket.util.ServerBeanUtil;

@WebSocket(maxIdleTime = 90000000)
public class ServerSocket {

    @OnWebSocketConnect
    public void onConnect(Session session) {
        System.out.println(session);
        //ChatBeanUtil.getStockpileService().addClient(session);
    }

    @OnWebSocketClose
    public void onClose(Session session, int _closeCode, String _closeReason) {
        ServerBeanUtil.getStockpileService().removeClient(session);
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message)
    {
        ServerBeanUtil.getStockpileService().handleMessage(message, session);
    }
}
