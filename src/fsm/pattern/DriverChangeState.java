package fsm.pattern;


import fsm.pattern.custom.*;

import java.util.Arrays;

import static fsm.pattern.DriverChangeEventType.*;

public enum DriverChangeState implements IState {

    DRIVER_CHANGE_PENDING() {
        @Override
        public Boolean isInitialState() {
            return Boolean.TRUE;
        }
    },
    DRIVER_CHANGE_COMPLETED() {
        @Override
        public Boolean isFinalState() {
            return Boolean.TRUE;
        }
    };

    @Override
    public String getName() {
        return this.getName();
    }

    public static FiniteStateMachine createStateMachine() {
        return StateMachineFactory.createStateMachine();
    }

    static class StateMachineFactory {

        private static final ActionFailPolicy[] ACTION_FAIL_FALSE_EXCEPTION = {ActionFailPolicy.FAIL_ON_FALSE,
                ActionFailPolicy.FAIL_ON_EXCEPTION};

        private StateMachineFactory() {
        }

        private static FiniteStateMachine createStateMachine() {

            return new BaseFiniteStateMachineFactory(DriverChangeState.class)
                    .addStateMachineListener(new MachineListener())

                    .addStateTransition(new StateTransition().from(DRIVER_CHANGE_PENDING).to(DRIVER_CHANGE_COMPLETED)
                                    .onEventTypes(Arrays.asList(SAVED_STATE_EVENT, MANAGER_APPROVAL_PENDING_STATE_EVENT, MANAGER_APPROVAL_REJECTED_STATE_EVENT, SAVED_PENDING_ASSET_SETUP_STATE_EVENT))
//                                    .addAction(new FirstAction().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addAction(new SecondAction().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addAction(new ThirdAction().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addListener(new FirstActionListener())
                                    .addAction(new SyncActionGroup()
                                            .addAction(new FirstAction())
                                            .addAction(new SecondExtended())
                                            .addAction(new ThirdAction())
                                            .addListener(new FirstActionListener())
                                            .setFailPolicies(GroupFailPolicy.FAIL_ON_ANY_FAILURE))
                    )
                    .addEventsListener(Arrays.asList(SAVED_STATE_EVENT, MANAGER_APPROVAL_PENDING_STATE_EVENT, MANAGER_APPROVAL_REJECTED_STATE_EVENT, SAVED_PENDING_ASSET_SETUP_STATE_EVENT), new FirstEventListener())
                    .addEventsListener(Arrays.asList(SAVED_STATE_EVENT, MANAGER_APPROVAL_PENDING_STATE_EVENT, MANAGER_APPROVAL_REJECTED_STATE_EVENT, SAVED_PENDING_ASSET_SETUP_STATE_EVENT), new SecondEventListener())
                    .build();

//                    .addStateTransition(new StateTransition(fsm).from(DRIVER_CHANGE_PENDING).to(DRIVER_CHANGE_PENDING)
//                            .onEventTypes(Arrays.asList(FACTORY_ORDER_SUBMITTED_STATE_EVENT, AFTER_APPROVAL_BEFORE_TRANSMISSION))
//                            .addAction(new SyncActionGroup()
//                                    .addAction(new ResolveDriverAction().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addAction(new UpdateDriverChangesAction().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addAction(new UpdateDriverGaragingAddressAction().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addAction(new RemoveDeliveryAddressAction().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addAction(new DriverInfoDeliveryAddressUpdate().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addAction(new DriverInfoRemoveDeliveryAddress().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addAction(new SaveOrderErrorsAction().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addListener(new TransactionGroupCompletionListener())
//                                    .addListener(new AutoAssignDealerForDriverChangeActionListener())
//                                    .addListener(new BillingChangeActionListener())
//                                    .addListener(new InsuranceCheckForDriverChangeActionListener())
//                                    .setFailPolicies(GroupFailPolicy.FAIL_ON_ANY_FAILURE)
//                            )
//                            .addAction(new SyncActionGroup()
//                                    .addAction(new RunAutoAssignDeliveringDealersAction().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addAction(new RunAutoAssignOrderingDealersAction().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addAction(new UpdateDeliveringPoForDealerIssuesAction().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addAction(new UpdateOrderingPoForDealerIssuesAction().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addAction(new UpdateDeliveringDealerOrderPoContactAction().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addAction(new UpdateOrderingDealerOrderPoContactAction().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addAction(new UpdateWhoToTitleAction().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addAction(new SaveCourtesyDeliveryFeeForDeliveringPoAction().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addAction(new CheckDropshipDealerContactForDeliveringPoAction().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addAction(new SaveOrderErrorsAction().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addListener(new TransactionGroupCompletionListener())
//                                    .setFailPolicies(GroupFailPolicy.FAIL_ON_ANY_FAILURE)
//                            )
//                            .addAction(new SyncActionGroup()
//                                    .addAction(new ResolveBillingAction().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addAction(new UpdateBillingAction().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addAction(new ClearSoftErrorAction().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addAction(new CreateSoftErrorAction().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addListener(new TransactionGroupCompletionListener())
//                                    .addListener(new TitleRegCheckForDriverChangeActionListener())
//                                    .setFailPolicies(GroupFailPolicy.FAIL_ON_ANY_FAILURE)
//                            )
//                            .addAction(new SyncActionGroup()
//                                    .addAction(new ResolveTitleAndLicenseAction().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addAction(new CheckTitleRegLienHolderUpdatesAction().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addAction(new UpdateOrderingPoForTitleAndRegIssuesAction().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addAction(new SaveOrderErrorsAction().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addListener(new TransactionGroupCompletionListener())
//                                    .setFailPolicies(GroupFailPolicy.FAIL_ON_ANY_FAILURE)
//                            )
//                            .addAction(new SyncActionGroup()
//                                    .addAction(new ResolveInsuranceAction().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addAction(new CheckInsuranceUpdatesAction().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addAction(new UpdateOrderingPoForInsuranceAction().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addAction(new SaveOrderErrorsAction().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addListener(new TransactionGroupCompletionListener())
//                                    .setFailPolicies(GroupFailPolicy.FAIL_ON_ANY_FAILURE)
//                            )
//                            .addAction(new AsyncActionGroup()
//                                    .addAction(new OEMTransmitDealerAssignmentAction().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addAction(new BridgeDriverChangeAction().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addAction(new BridgeGaragingAddressChangeAction().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addAction(new BridgeDealerDetailsAction().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addAction(new BridgeBillingDetailsAction().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addAction(new BridgeTitleRegLienDetailsAction().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addAction(new BridgeInsuranceDetailsAction().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addAction(new BridgeOrderMaintenanceChangeAction().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addListener(new GroupCompletionListener())
//                                    .setFailPolicies(GroupFailPolicy.FAIL_NEVER)
//                            )
//                    )
//                    .addEventListener(FACTORY_ORDER_SUBMITTED_STATE_EVENT, new CorpClientEventListener())
//
//                    .addStateTransition(new StateTransition(fsm).from(DRIVER_CHANGE_PENDING).to(DRIVER_CHANGE_PENDING)
//                            .onEventTypes(Arrays.asList(FACTORY_ORDER_ACKNOLEDGED_STATE_EVENT, FACTORY_ORDER_ALLOCATED_STATE_EVENT, FACTORY_ORDER_APPROVED_STATE_EVENT,
//                                    FACTORY_ORDER_BUILT_STATE_EVENT, FACTORY_ORDER_ORDERED_STATE_EVENT, FACTORY_ORDER_SCHEDULED_STATE_EVENT, FACTORY_ORDER_SHIPPED_STATE_EVENT,
//                                    AFTER_APPROVAL_AFTER_TRANSMISSION))
//                            .addAction(new SyncActionGroup()
//                                    .addAction(new ResolveDriverAction().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addAction(new UpdateDriverChangesAction().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addAction(new UpdateDriverGaragingAddressAction().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addAction(new RemoveDeliveryAddressAction().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addAction(new DriverInfoDeliveryAddressUpdate().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addAction(new DriverInfoRemoveDeliveryAddress().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addListener(new TransactionGroupCompletionListener())
//                                    .addListener(new AutoAssignDealerForDriverChangeActionListener())
//                                    .setFailPolicies(GroupFailPolicy.FAIL_ON_ANY_FAILURE)
//                            )
//                            .addAction(new SyncActionGroup()
//                                    .addAction(new RunAutoAssignDeliveringDealersAction().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addAction(new RunAutoAssignOrderingDealersAction().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addAction(new UpdateDeliveringPoForDealerIssuesAction().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addAction(new UpdateOrderingPoForDriverIssuesAction().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addAction(new SaveProposedDeliveringDealerOrderPoContactAction().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addAction(new CheckDropshipDealerContactForDeliveringPoAction().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addAction(new SaveOrderErrorsAction().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addListener(new TransactionGroupCompletionListener())
//                                    .setFailPolicies(GroupFailPolicy.FAIL_ON_ANY_FAILURE)
//                            )
//                            .addAction(new AsyncActionGroup()
//                                    .addAction(new OEMTransmitDealerAssignmentAction().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addAction(new BridgeDriverChangeAction().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addAction(new BridgeGaragingAddressChangeAction().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addAction(new BridgeOrderMaintenanceChangeAction().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addListener(new GroupCompletionListener())
//                                    .setFailPolicies(GroupFailPolicy.FAIL_NEVER)
//                            )
//                    )
//                    .addEventsListener(Arrays.asList(FACTORY_ORDER_ACKNOLEDGED_STATE_EVENT, FACTORY_ORDER_ALLOCATED_STATE_EVENT, FACTORY_ORDER_APPROVED_STATE_EVENT,
//                            FACTORY_ORDER_BUILT_STATE_EVENT, FACTORY_ORDER_ORDERED_STATE_EVENT, FACTORY_ORDER_SCHEDULED_STATE_EVENT, FACTORY_ORDER_SHIPPED_STATE_EVENT), new CorpClientEventListener())
//
//                    .addStateTransition(new StateTransition(fsm).from(DRIVER_CHANGE_PENDING).to(DRIVER_CHANGE_PENDING)
//                            .onEventTypes(Arrays.asList(FACTORY_ORDER_DELIVERED_TO_DRIVER_STATE_EVENT))
//                            .addAction(new SyncActionGroup()
//                                    .addAction(new ResolveDriverAction().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addAction(new UpdateDriverChangesAction().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addAction(new UpdateDriverGaragingAddressAction().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addAction(new RemoveDeliveryAddressAction().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addAction(new DriverInfoDeliveryAddressUpdate().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addAction(new DriverInfoRemoveDeliveryAddress().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addListener(new TransactionGroupCompletionListener())
//                                    .setFailPolicies(GroupFailPolicy.FAIL_ON_ANY_FAILURE)
//                            )
//                            .addAction(new AsyncActionGroup()
//                                    .addAction(new OEMTransmitDealerAssignmentAction().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addAction(new BridgeDriverChangeAction().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addAction(new BridgeGaragingAddressChangeAction().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addAction(new BridgeOrderMaintenanceChangeAction().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addListener(new GroupCompletionListener())
//                                    .setFailPolicies(GroupFailPolicy.FAIL_NEVER)
//                            )
//                    )
//                    .addEventsListener(Arrays.asList(FACTORY_ORDER_DELIVERED_TO_DRIVER_STATE_EVENT), new CorpClientEventListener())
//
//                    .addStateTransition(new StateTransition(fsm).from(DRIVER_CHANGE_PENDING).to(DRIVER_CHANGE_COMPLETED).onEventType(STOCK_AND_DEALER_ORDER_SUBMITTED_STATE_EVENT)
//                            .addAction(new SyncActionGroup()
//                                    .addAction(new ResolveDriverAction().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addAction(new UpdateDriverChangesAction().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addAction(new UpdateDriverGaragingAddressAction().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addAction(new RemoveDeliveryAddressAction().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addAction(new DriverInfoDeliveryAddressUpdate().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addAction(new DriverInfoRemoveDeliveryAddress().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addAction(new UpdateOrderingPoForTitleRegAndInsuranceIssuesAction().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addAction(new UpdateStockAndDealerOrderPoForDriverIssuesAction().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addAction(new SaveOrderErrorsAction().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addListener(new TransactionGroupCompletionListener())
//                                    .addListener(new InsuranceCheckForDriverChangeActionListener())
//                                    .addListener(new TitleRegCheckForDriverChangeActionListener())
//                                    .setFailPolicies(GroupFailPolicy.FAIL_ON_ANY_FAILURE)
//                            )
//                            .addAction(new SyncActionGroup()
//                                    .addAction(new CheckInsuranceUpdatesAction().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addAction(new SaveOrderErrorsAction().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addListener(new TransactionGroupCompletionListener())
//                                    .setFailPolicies(GroupFailPolicy.FAIL_ON_ANY_FAILURE)
//                            )
//                            .addAction(new SyncActionGroup()
//                                    .addAction(new CheckTitleRegLienHolderUpdatesAction().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addAction(new SaveOrderErrorsAction().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addListener(new TransactionGroupCompletionListener())
//                                    .setFailPolicies(GroupFailPolicy.FAIL_ON_ANY_FAILURE)
//                            )
//                            .addAction(new AsyncActionGroup()
//                                    .addAction(new OEMTransmitDealerAssignmentAction().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addAction(new BridgeDriverChangeAction().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addAction(new BridgeGaragingAddressChangeAction().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addAction(new BridgeTitleRegLienDetailsAction().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addAction(new BridgeInsuranceDetailsAction().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addAction(new BridgeOrderMaintenanceChangeAction().setFailPolicies(ACTION_FAIL_FALSE_EXCEPTION))
//                                    .addListener(new GroupCompletionListener())
//                                    .setFailPolicies(GroupFailPolicy.FAIL_NEVER)
//                            )
//                    )
//                    .addEventListener(STOCK_AND_DEALER_ORDER_SUBMITTED_STATE_EVENT, new CorpClientEventListener())
//                    .build();
        }
    }
}
