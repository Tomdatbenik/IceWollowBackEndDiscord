package serverwebsocket.service;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.eclipse.jetty.websocket.api.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import serverwebsocket.factory.InstanceFactory;
import serverwebsocket.interfaces.IHandler;
import serverwebsocket.managers.ServerManager;
import serverwebsocket.messages.BaseMessage;
import serverwebsocket.models.Client;
import servercomponent.service.ServerService;


import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Service("serverSvc")
@AllArgsConstructor
public class ServerSocketService {

//    private ScheduledExecutorService scheduledClientManagerExecutor;
    private Gson gson = new Gson();

    @Autowired
    private final ServerService service;

    public void addClient(Session session) {

    }

    public void removeClient(Session session) {
        ServerManager serverManager = ServerManager.getInstance(service);
        Client client = serverManager.getObserverBySession(session).getClient();
        serverManager.removeClient(client);
    }

    @PostConstruct
    private void init() {
//        scheduledClientManagerExecutor = Executors.newScheduledThreadPool(20);
    }

    //TODO create helloworld handler from message
    public void handleMessage(String sMessage, Session session) {
        Logger logger = LoggerFactory.getLogger(ServerSocketService.class);
        BaseMessage message = gson.fromJson(sMessage, BaseMessage.class);

        try{
            Class Clazz = Class.forName("serverwebsocket.handlers." + message.getHandler());

            InstanceFactory<IHandler> factory = new InstanceFactory<>();
            logger.info("Handler is: " + Clazz.getName());
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
