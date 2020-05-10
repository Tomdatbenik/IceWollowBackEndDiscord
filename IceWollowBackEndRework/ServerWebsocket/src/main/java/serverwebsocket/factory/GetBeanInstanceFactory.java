package serverwebsocket.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class GetBeanInstanceFactory<T> implements ApplicationContextAware {

    private final Logger logger = LoggerFactory.getLogger(GetBeanInstanceFactory.class);

    private static ApplicationContext context;

    public T Create(Class<T> aClass) {
        return context.getBean(aClass.getSimpleName(), aClass);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
