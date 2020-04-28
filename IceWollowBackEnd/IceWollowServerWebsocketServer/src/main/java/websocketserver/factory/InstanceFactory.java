package websocketserver.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import websocketserver.service.ServerService;

import java.lang.reflect.InvocationTargetException;

public class InstanceFactory<T> {

    private final Logger logger = LoggerFactory.getLogger(ServerService.class);

    public T Create(Class<T> aClass){
        try {
            return aClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException e) {
            logger.error(e.getMessage());
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage());
        } catch (InvocationTargetException e) {
            logger.error(e.getMessage());
        } catch (NoSuchMethodException e) {
            logger.error(e.getMessage());
        }

        return null;
    };
}
