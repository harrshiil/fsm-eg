package fsm.pattern;

/**
 * This enum holds event types for driver change
 * @author KMorajkar
 *
 */
public enum DriverChangeEventType implements IEventType {

	SAVED_STATE_EVENT,
	MANAGER_APPROVAL_PENDING_STATE_EVENT,
	MANAGER_APPROVAL_REJECTED_STATE_EVENT,
	SAVED_PENDING_ASSET_SETUP_STATE_EVENT,
	FACTORY_ORDER_SUBMITTED_STATE_EVENT,
	FACTORY_ORDER_APPROVED_STATE_EVENT,
	FACTORY_ORDER_ALLOCATED_STATE_EVENT,
	FACTORY_ORDER_SCHEDULED_STATE_EVENT,
	FACTORY_ORDER_BUILT_STATE_EVENT,
	FACTORY_ORDER_ORDERED_STATE_EVENT,
	FACTORY_ORDER_ACKNOLEDGED_STATE_EVENT,
	FACTORY_ORDER_SHIPPED_STATE_EVENT,
	STOCK_AND_DEALER_ORDER_SUBMITTED_STATE_EVENT,
	AFTER_APPROVAL_BEFORE_TRANSMISSION,
	AFTER_APPROVAL_AFTER_TRANSMISSION,
	FACTORY_ORDER_DELIVERED_TO_DRIVER_STATE_EVENT
}