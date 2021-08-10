package fsm.pattern;

public interface IEventHandler<T> {

    T onEvent(IEvent event);

}
