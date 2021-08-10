package fsm.pattern;

import java.util.List;

public class BaseFiniteStateMachineFactory implements IStateMachineFactory {

    protected FiniteStateMachine fsm;

    public BaseFiniteStateMachineFactory(Class<? extends IState> enumClass) {
        for (IState state : enumClass.getEnumConstants()) {
            if (state.isInitialState()) {
                this.fsm = new FiniteStateMachine(state);
                break;
            }
        }
        if (this.fsm == null) {
            this.fsm = new FiniteStateMachine(enumClass.getEnumConstants()[0]);
        }
        for (IState state : enumClass.getEnumConstants()) {
            this.fsm.addState(state);
        }
    }

    public BaseFiniteStateMachineFactory addStateTransition(final IStateTransition transition) {
        fsm.addStateTransition(transition.getInitialState(), transition);
        return this;
    }

    public BaseFiniteStateMachineFactory addStateMachineListener(final IStateMachineListener listener) {
        fsm.addStateMachineListener(listener);
        return this;
    }

    public BaseFiniteStateMachineFactory addEventListener(final IEventType eventType, final IEventListener listener) {
        fsm.addEventListener(eventType, listener);
        return this;
    }

    public BaseFiniteStateMachineFactory addStateListener(final IState state, final IStateListener listener) {
        fsm.addStateListener(state, listener);
        return this;
    }

    public BaseFiniteStateMachineFactory addPreStateGuard(IState state, IGuard guard) {
        fsm.addPreStateGuard(state, guard);
        return this;
    }

    public BaseFiniteStateMachineFactory addPostStateGuard(IState state, IGuard guard) {
        fsm.addPostStateGuard(state, guard);
        return this;
    }

    public FiniteStateMachine getStateMachine() {
        return fsm;
    }

    @Override
    public FiniteStateMachine build() {
        return fsm.lock();
    }

    public BaseFiniteStateMachineFactory addEventsListener(final List<IEventType> eventTypes, final IEventListener listener) {
        for (IEventType eventType : eventTypes) {
            fsm.addEventListener(eventType, listener);
        }
        return this;
    }

}
