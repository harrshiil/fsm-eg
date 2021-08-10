package fsm.pattern;

// for logging, counters, block event from processing etc...
public interface IEventListener {

    default void onEvent(IEvent event, IContext context) {}
    default void afterEvent(IEvent event, IContext context) {}
}
