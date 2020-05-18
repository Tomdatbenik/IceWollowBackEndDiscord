package serverwebsocket.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import serverwebsocket.service.ServerSocketService;


public class ServerBeanUtil implements ApplicationContextAware {
    private static ApplicationContext appCxt;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        appCxt = applicationContext;
    }

    public static ServerSocketService getStockpileService() throws BeansException {
        return (ServerSocketService) appCxt.getAutowireCapableBeanFactory().getBean("serverSvc");
    }
}
