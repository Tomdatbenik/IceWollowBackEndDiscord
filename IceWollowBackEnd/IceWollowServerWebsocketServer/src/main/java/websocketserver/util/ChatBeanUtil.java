package websocketserver.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import websocketserver.service.ChatService;;

public class ChatBeanUtil implements ApplicationContextAware {
    private static ApplicationContext appCxt;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        appCxt = applicationContext;
    }

    public static ChatService getStockpileService() throws BeansException {
        return (ChatService) appCxt.getAutowireCapableBeanFactory().getBean("chatSvc");
    }
}
