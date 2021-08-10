package fsm.pattern;

import java.time.Duration;
import java.util.*;

public abstract class ActionGroup extends Action<IFSMActionGroupResult> implements IFSMActionGroup {

    public static final Duration DEFAULT_MAX_DURATION = Duration.ofDays(1);

    protected Collection<IFSMAction<?>> actions;
    protected ActionGroupResult result;

    protected WaitPolicy waitPolicy;
    protected CancelPolicy cancelPolicy;

    protected ActionGroup() {
        super();
        this.actions = new ArrayList<>();
        this.result = new ActionGroupResult(0);
    }

    @Override
    public ActionGroup setFailPolicies(IFSMActionPolicy... policies) {
        this.failPolicies = new LinkedHashSet<>();
        Collections.addAll(this.failPolicies, policies);
        if (this.failPolicies.size() > 1) {
            this.failPolicies.remove(GroupFailPolicy.FAIL_NEVER);
        }
        if (this.failPolicies.isEmpty()) {
            failPolicies.add(GroupFailPolicy.FAIL_NEVER);
        }
        return this;
    }

    @Override
    public IFSMActionGroupResult execute(IContext context) {
        throw new UnsupportedOperationException("execute() is not supported for action groups");
    }

    @Override
    public Optional<IFSMActionGroupResult> getDefaultValue(IContext context) {
        throw new UnsupportedOperationException("getDefaultValue() is not supported for action groups");
    }

    @Override
    public Collection<IFSMAction<?>> getActions() {
        return this.actions;
    }

    public ActionGroup addAction(final IFSMAction<?> action) {
        this.actions.add(action);
        this.result.onAdd();
        return this;
    }

    public ActionGroup setWaitPolicy(WaitPolicy waitPolicy) {
        this.waitPolicy = waitPolicy;
        return this;
    }

    public ActionGroup setCancelPolicy(CancelPolicy cancelPolicy) {
        this.cancelPolicy = cancelPolicy;
        return this;
    }

    @Override
    public WaitPolicy getWaitPolicy() {
        return this.waitPolicy;
    }

    @Override
    public CancelPolicy getCancelPolicy() {
        return this.cancelPolicy;
    }

    @Override
    public IFSMActionGroupResult getResult() {
        return this.result;
    }

    public long waitTime() {
        Duration actionGroupTimeout = getTimeout();
        return DEFAULT_MAX_DURATION.compareTo(actionGroupTimeout) >= 0 ? actionGroupTimeout.toMillis()
                : DEFAULT_MAX_DURATION.toMillis();
    }

    @Override
    public boolean hasStarted(IContext context) {
        return this.isRunning(context) || this.hasCompleted(context);
    }

    @Override
    public boolean isRunning(IContext context) {
        return this.result.getRunningCount() > 0;
    }

    @Override
    public boolean hasCompleted(IContext context) {
        return this.result.getTotalCount() == this.result.getSuccessCount() + this.result.getFailureCount() + this.result.getSkipCount();
    }

}
