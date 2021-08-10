package fsm.pattern;

public class SyncActionGroup extends ActionGroup {

//    private static final Logger LOGGER = LoggerFactory.getLogger(SyncActionGroup.class);

    public SyncActionGroup() {
        super();
        setFailPolicies(GroupFailPolicy.FAIL_ON_ANY_FAILURE);
        this.waitPolicy = WaitPolicy.WAIT_ANY_FAILURE;
        this.cancelPolicy = CancelPolicy.CANCEL_ALL;
    }

    @Override
    public void run(IContext context) {
//        LOGGER.debug("Begin run() of sync action group: {}", this.toString());
//        LOGGER.trace("Context={} of sync action group {}", context, this.toString());
//        LOGGER.debug("On entry {}", SecurityContext.getSecurityAttributes().toString());
        if (!isApplicable(context)) {
//            LOGGER.debug("Sync action group {} is not applicable, exiting...", this.toString());
            return;
        }
        context.bind(getGUID(), result);
//        LOGGER.debug("Added result {} to context of sync action group {}", result, this.toString());
        long timeToWait = waitTime();
        long startTime = System.currentTimeMillis();
//        LOGGER.debug("Time to wait {}ms for sync action group {}", timeToWait, this.toString());
        for (IFSMAction<?> action : actions) {
            try {
                result.onStart();
                if (action.isApplicable(context)) {
                    action.run(context);
                    result.onSuccess();
                } else {
                    result.onSkip();
                }
            } catch (FSMException e) {
//                LOGGER.debug("Exception {} during sync action group: {}", e.getMessage(), this.toString());
                result.onFailure();
            }
            long timeElapsed = System.currentTimeMillis() - startTime;
//            LOGGER.debug("Elapsed time  {}ms for sync action group {}", timeElapsed, this.toString());
            if ( !this.hasCompleted(context) && (getWaitPolicy().evaluate(this, context) || timeElapsed > timeToWait)) {
//                LOGGER.debug("Exiting sync action group {} due to {}",  this.toString(), this.getWaitPolicy());
                actions.stream().filter(a -> !a.hasStarted(context) && a.isApplicable(context)).forEach(a -> result.onStart().onSkip());
                return;
            }
        }
//        LOGGER.debug("On exit {}", SecurityContext.getSecurityAttributes().toString());
        long timeTotal = System.currentTimeMillis() - startTime;
//        LOGGER.debug("Completed within {}ms sync action group {}", timeTotal, this.toString());

    }

   

}
