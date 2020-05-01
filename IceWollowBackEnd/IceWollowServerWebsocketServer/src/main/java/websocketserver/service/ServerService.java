package websocketserver.service;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import settlementcomponent.Application;
import websocketserver.factory.InstanceFactory;
import websocketserver.interfaces.IHandler;
import websocketserver.managers.ServerManager;
import websocketserver.messages.BaseMessage;
import websocketserver.models.Client;

import javax.annotation.PostConstruct;

@Service("serverSvc")
public class ServerService {

    // private ScheduledExecutorService scheduledClientManagerExecutor;
    private Gson gson = new Gson();
    private final Logger logger = LoggerFactory.getLogger(ServerService.class);

    public void addClient(Session session) {

    }

    public void removeClient(Session session) {
        ServerManager serverManager = ServerManager.getInstance();
        Client client = serverManager.getObserverBySession(session).getClient();
        serverManager.removeClient(client);
    }

    @PostConstruct
    private void init() {
        Application.setUpProperties();

        //scheduledClientManagerExecutor = Executors.newScheduledThreadPool(20);
    }

    //TODO create helloworld handler from message
    public void handleMessage(String sMessage, Session session) {
        BaseMessage message = gson.fromJson(sMessage, BaseMessage.class);

        try{
            Class Clazz = Class.forName("websocketserver.handlers." + message.getHandler());

            InstanceFactory<IHandler> factory = new InstanceFactory<>();

            IHandler handler = factory.Create(Clazz);

            if(handler != null)
            {
                handler.Handle(gson.fromJson(sMessage,handler.getEmptyMessageObject().getClass()),session);
            }
            else
            {
                logger.error("Handler cannot be created.");
            }
        }
        catch (ClassNotFoundException ex)
        {
            logger.error(ex.getMessage());
        }
    }
}
