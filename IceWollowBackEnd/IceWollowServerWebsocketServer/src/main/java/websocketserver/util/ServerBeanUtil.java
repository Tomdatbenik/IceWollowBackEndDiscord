package websocketserver.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import websocketserver.service.ServerService;;

public class ServerBeanUtil implements ApplicationContextAware {
    private static ApplicationContext appCxt;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        appCxt = applicationContext;
    }

    public static ServerService getStockpileService() throws BeansException {
        return (ServerService) appCxt.getAutowireCapableBeanFactory().getBean("serverSvc");
    }
}
