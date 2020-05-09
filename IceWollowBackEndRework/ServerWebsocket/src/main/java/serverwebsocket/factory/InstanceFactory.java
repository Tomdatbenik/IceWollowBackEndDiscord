package serverwebsocket.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.sql.DriverManager;

@Component
public class InstanceFactory<T> implements ApplicationContextAware {

    private final Logger logger = LoggerFactory.getLogger(InstanceFactory.class);

    private static ApplicationContext context;

    public T Create(Class<T> aClass) {
        return context.getBean(aClass.getSimpleName(), aClass);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
