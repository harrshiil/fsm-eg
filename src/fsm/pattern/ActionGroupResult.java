package fsm.pattern;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

public class ActionGroupResult implements IFSMActionGroupResult, Serializable {

    private static final long serialVersionUID = 1091277162303472317L;

    private final AtomicInteger totalCount;
    private final AtomicInteger successCount;
    private final AtomicInteger failureCount;
    private final AtomicInteger runningCount;
    private final AtomicInteger skipCount;

    public ActionGroupResult() {
        this(0);
    }

    public ActionGroupResult(int numberActions) {
        if (numberActions < 0) {
            throw new IllegalArgumentException("Invalid number of actions: " + numberActions);
        }
        this.totalCount = new AtomicInteger(numberActions);
        this.successCount = new AtomicInteger(0);
        this.failureCount = new AtomicInteger(0);
        this.runningCount = new AtomicInteger(0);
        this.skipCount = new AtomicInteger(0);
    }

    @Override
    public int getTotalCount() {
        return this.totalCount.get();
    }

    @Override
    public int getSuccessCount() {
        return this.successCount.get();
    }

    @Override
    public int getFailureCount() {
        return this.failureCount.get();
    }

    @Override
    public int getRunningCount() {
        return this.runningCount.get();
    }

    @Override
    public int getSkipCount() {
        return this.skipCount.get();
    }

    public ActionGroupResult onSuccess() {
        if (this.runningCount.get() < 1) {
            throw new FSMException("No actions running, but success was reported");
        }
        this.successCount.incrementAndGet();
        this.runningCount.decrementAndGet();
        return this;
    }

    public ActionGroupResult onFailure() {
        if (this.runningCount.get() < 1) {
            throw new FSMException("No actions running, but failure was reported");
        }
        this.failureCount.incrementAndGet();
        this.runningCount.decrementAndGet();
        return this;
    }

    public ActionGroupResult onStart() {
        this.runningCount.incrementAndGet();
        return this;
    }

    public ActionGroupResult onSkip() {
        if (this.runningCount.get() < 1) {
            throw new FSMException("No actions running, but failure was reported");
        }
        this.skipCount.incrementAndGet();
        this.runningCount.decrementAndGet();
        return this;
    }

    public ActionGroupResult onAdd() {
        this.totalCount.incrementAndGet();
        return this;
    }

    @Override
    public String toString() {
        return "ActionGroupResult{" +
                "totalCount=" + totalCount +
                ", successCount=" + successCount +
                ", failureCount=" + failureCount +
                ", runningCount=" + runningCount +
                ", skipCount=" + skipCount +
                '}';
    }
}
