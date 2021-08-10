package fsm.pattern;

public interface IActionListener {

    default void onEntry(@SuppressWarnings("rawtypes") IAction action, IContext context) {}
    default void onExit(@SuppressWarnings("rawtypes") IAction action, IContext context) {}
}
