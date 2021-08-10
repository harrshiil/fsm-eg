package fsm.pattern;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class CommonOrderEvent extends BaseEvent {
    private final List<String> exceptionList;

    public CommonOrderEvent(String message, Map<String, Object> payload) {
        super(message, payload);
        this.exceptionList = new ArrayList<>();
    }

    public CommonOrderEvent(String source, String message, Map<String, Object> payload) {
        super(source, message, payload);
        this.exceptionList = new ArrayList<>();
    }

    @Override
    public List<String> getExceptionList() {
        return exceptionList;
    }

    @Override
    public String toString() {
        return "CommonOrderEvent{" +
                "source='" + source + '\'' +
                ", message='" + message + '\'' +
                ", payload=" + payload +
                ", exceptionList=" + exceptionList +
                '}';
    }
}
