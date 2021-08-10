package fsm.pattern;

import java.util.Collection;

public interface IStateTransition {

    // "from" state
    IState getInitialState();

    // "to" state, could could be the same as "from"
    IState getFinalState();

    Collection<IEventType> getTriggerEventTypes();
    
    Collection<IFSMAction<?>> getActions(); // NOSONAR
    
    Collection<IStateTransitionListener> getListeners();

}
