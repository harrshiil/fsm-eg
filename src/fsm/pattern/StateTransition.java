package fsm.pattern;

import java.util.ArrayList;
import java.util.Collection;

import static java.util.Objects.requireNonNull;

public class StateTransition implements IStateTransition {

    private IState initialState;
    private IState finalState;
    private Collection<IEventType> triggerEventTypes;
    private Collection<IFSMAction<?>> actions;
    private Collection<IStateTransitionListener> listeners;

    public StateTransition() {
//        requireNonNull(stateMachine, "StateTransition requires not-null IStateMachine");
        this.initialState = null;
        this.finalState = null;
        this.actions = new ArrayList<>();
        this.listeners = new ArrayList<>();
        this.triggerEventTypes = new ArrayList<>();
    }

    public StateTransition from(final IState fromState) {
        requireNonNull(fromState, "StateTransition requires not-null fromState");
        this.initialState = fromState;
        return this;
    }

    public StateTransition to(final IState toState) {
        requireNonNull(toState, "StateTransition requires not-null toState");
        this.finalState = toState;
        return this;
    }

    public StateTransition onEventType(final IEventType trigger) {
        requireNonNull(trigger, "StateTransition requires not-null trigger event type");
        this.triggerEventTypes.add(trigger);
        return this;
    }

    public StateTransition onEventTypes(final Collection<IEventType> triggers) {
        for (IEventType trigger : triggers) {
            onEventType(trigger);
        }
        return this;
    }

    public StateTransition addAction(final IFSMAction<?> action) {
        requireNonNull(action, "StateTransition requires not-null action");
        this.actions.add(action);
        return this;
    }

    public StateTransition addListener(final IStateTransitionListener listener) {
        requireNonNull(listener, "StateTransition requires not-null listener");
        this.listeners.add(listener);
        return this;
    }

    @Override
    public IState getInitialState() {
        return this.initialState;
    }

    @Override
    public IState getFinalState() {
        return this.finalState;
    }

    @Override
    public Collection<IEventType> getTriggerEventTypes() {
        return this.triggerEventTypes;
    }

    @Override
    public Collection<IFSMAction<?>> getActions() {
        return this.actions;
    }

    @Override
    public Collection<IStateTransitionListener> getListeners() {
        return this.listeners;
    }

}
