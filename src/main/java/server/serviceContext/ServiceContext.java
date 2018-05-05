package server.serviceContext;

import java.util.HashMap;
import java.util.Map;

public class ServiceContext {
    private Map<Class<?>, Object> context = new HashMap<>();

    public void add(Class<?> clazz, Object object) throws Exception {
        if (context.containsKey(clazz)) {
            throw new Exception("Service already added!");
        }
        context.put(clazz, object);
    }

    public Object get(Class<?> clazz) {
        return context.get(clazz);
    }
}
