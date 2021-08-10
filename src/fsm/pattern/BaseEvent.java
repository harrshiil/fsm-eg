package fsm.pattern;

import java.time.LocalDateTime;
import java.util.*;

//@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public abstract class BaseEvent implements IEvent {
    private static final long serialVersionUID = 2736923431666226938L;

    protected static Set<String> keys = new HashSet<>();

    private String id;
    //    @JacksonLocalDateTime
    private LocalDateTime timestamp; // NOSONAR
    protected String source;
    protected String message;
    protected Map<String, Object> payload;

    protected BaseEvent() {
        this.id = UUID.randomUUID().toString();
        this.payload = new HashMap<>();
        this.timestamp = LocalDateTime.now();
    }

    protected BaseEvent(String message, final Map<String, Object> data) {
        this();
        this.message = message;
        if (null != data) {
            data.keySet().forEach(key -> this.payload.put(key, data.get(key)));
        }
        this.payload = Collections.unmodifiableMap(this.payload);
    }

    protected BaseEvent(String source, String message, final Map<String, Object> data) {
        this(message, data);
        this.source = source;
    }

//
//    protected BaseEvent(String message, String payloadString) throws IOException {
//        this(message, Collections.unmodifiableMap(JacksonUtil.OBJECT_MAPPER.readValue(payloadString, new TypeReference<Map<String, Object>>() {
//        })));
//    }

    @SuppressWarnings("unchecked")
    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    @Override
    public Map<String, Object> getPayload() {
        return this.payload;
    }

//    public String toJSON() throws JsonProcessingException {
//        return JacksonUtil.toStringLazyLoad(this);
//    }

    public Object getValue(String key) {
        return payload.get(key);
    }

    public Boolean hasKey(String key) {
        return payload.containsKey(key);
    }

    //    @JsonIgnore
    public Set<String> getDefinedKeys() {
        return keys;
    }

    public Boolean hasNonEmptyValue(String key) {
        if (!hasKey(key)) {
            return false;
        }
        Object value = getValue(key);
        if (null == value) {
            return false;
        }
        if (value instanceof String) {

//            return !StringUtils.isEmpty(value.toString());
        }
        return true;
    }

    //    @JsonProperty("eventType") // NOSONAR
    abstract public IEventType getEventType(); // NOSONAR

//    @JsonIgnore
    public List<String> getExceptionList() {
    	return Collections.emptyList();
    }

    @Override
    public String getSource() {
        return this.source;
    }
}