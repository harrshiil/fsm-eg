package fsm.pattern;

import java.time.LocalDateTime;
import java.util.Map;

public interface IEvent {

    IEventType getEventType();

    String getMessage();

    Map<String, Object> getPayload();

    LocalDateTime getTimestamp();

    String getId();

    default String getSource() {
        return "";
    }
}
