package fsm.pattern;

public interface IStateListener {

    default void onEntry(IState state, IContext context) {}
    default void onExit(IState state, IContext context) {}
}
