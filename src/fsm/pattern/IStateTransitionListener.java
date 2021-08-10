package fsm.pattern;

public interface IStateTransitionListener {

    default void onEntry(IStateTransition transition, IContext context) {}
    default void onExit(IStateTransition transition, IContext context) {}
  
}
