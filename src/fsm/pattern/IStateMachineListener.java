package fsm.pattern;

// for logging, counters, c...
public interface IStateMachineListener {

    default void onEntry(IEvent event, IContext context) { //NOSONAR
        context.bind(IStateMachine.CURRENT_EVENT_KEY, event);
    }

    // store current event to context
    static void addEventToContext(final IContext context, final IEvent event) {
        context.bind(IStateMachine.CURRENT_EVENT_KEY, event);
    }

    default void onExit(IEvent event, IContext context) {}
}
