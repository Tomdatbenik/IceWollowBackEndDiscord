package websocketserver.factory;

import java.lang.reflect.InvocationTargetException;

public class HandlerFactory<T> {
    public T Create(Class<T> aClass) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        return aClass.getDeclaredConstructor().newInstance();
    };
}
