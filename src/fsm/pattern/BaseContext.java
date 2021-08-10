package fsm.pattern;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class BaseContext implements IContext {

    protected Map<String, Object> attributes;

    public BaseContext() {
        attributes = new ConcurrentHashMap<>();
    }

    @Override
    public void bind(String name, Object obj) {
        attributes.put(name, null == obj ? Optional.empty() : obj);
    }

    @Override
    public void close() {
        attributes = null;
    }

    @Override
    public Object lookup(String name) {
        return attributes.get(name);
    }

    @Override
    public void unbind(String name) {
        attributes.remove(name);
    }

    @Override
    public Boolean isPresent(String name) {
        return attributes.containsKey(name);
    }

    @Override
    public Boolean isEmpty(String name) {
        return !hasValue(name);
    }

    @Override
    public Boolean hasValue(String name) {
        Object value = lookup(name);
        return (value != null) && (!(value instanceof Optional) || ((Optional<?>) value).isPresent());
    }

    @Override
    public Collection<String> getNames() {
        return attributes.keySet();
    }

    @Override
    public String toString() {
        return "BaseContext{" +
                "attributes=" + attributes +
                '}';
    }
}
