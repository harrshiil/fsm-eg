/**
 * 
 */
package fsm.pattern;

import java.util.HashMap;
import java.util.Map;

import static fsm.pattern.DriverChangeEventType.SAVED_STATE_EVENT;

/**
 * This class create event based on event type passed to it
 * 
 * @author KMorajkar
 *
 */
public class DriverChangeEventFactory {

//	private static final Logger LOGGER = LoggerFactory.getLogger(DriverChangeEventFactory.class);

	private interface EventFactory {
		CommonOrderEvent create(Map<String, Object> orderData);
	}

	private static final Map<String, EventFactory> factoryMap = new HashMap<>();
	private static final Map<String, String> orderStateMap = new HashMap<>();

	static {
//		factoryMap.put(FACTORY_ORDER_ACKNOLEDGED_STATE_EVENT.toString(), FactoryOrderAcknowledgedStateEvent::new);
//		factoryMap.put(FACTORY_ORDER_ALLOCATED_STATE_EVENT.toString(), FactoryOrderAllocatedStateEvent::new);
//		factoryMap.put(FACTORY_ORDER_APPROVED_STATE_EVENT.toString(), FactoryOrderApprovedStateEvent::new);
//		factoryMap.put(FACTORY_ORDER_BUILT_STATE_EVENT.toString(), FactoryOrderBuiltStateEvent::new);
//		factoryMap.put(FACTORY_ORDER_ORDERED_STATE_EVENT.toString(), FactoryOrderOrderedStateEvent::new);
//		factoryMap.put(FACTORY_ORDER_SCHEDULED_STATE_EVENT.toString(), FactoryOrderScheduledStateEvent::new);
//		factoryMap.put(FACTORY_ORDER_SHIPPED_STATE_EVENT.toString(), FactoryOrderShippedStateEvent::new);
//		factoryMap.put(FACTORY_ORDER_SUBMITTED_STATE_EVENT.toString(), FactoryOrderSubmittedStateEvent::new);
//		factoryMap.put(MANAGER_APPROVAL_PENDING_STATE_EVENT.toString(), ManagerApprovalPendingStateEvent::new);
//		factoryMap.put(MANAGER_APPROVAL_REJECTED_STATE_EVENT.toString(), ManagerApprovalRejectedStateEvent::new);
//		factoryMap.put(SAVED_PENDING_ASSET_SETUP_STATE_EVENT.toString(), SavedPendingAssetSetupStateEvent::new);
		factoryMap.put(SAVED_STATE_EVENT.toString(), SavedStateEvent::new);
//		factoryMap.put(STOCK_AND_DEALER_ORDER_SUBMITTED_STATE_EVENT.toString(),
//				StockAndDealerOrderSubmittedStateEvent::new);
//		factoryMap.put(AFTER_APPROVAL_BEFORE_TRANSMISSION.toString(), AfterApprovalBeforeTransmissionEvent::new);
//		factoryMap.put(AFTER_APPROVAL_AFTER_TRANSMISSION.toString(), AfterApprovalAfterTransmissionEvent::new);
//		factoryMap.put(FACTORY_ORDER_DELIVERED_TO_DRIVER_STATE_EVENT.toString(), FactoryOrderDeliveredToDriverEvent::new);
	}
	
//	static {
//		orderStateMap.put(OrderStateType.Value.ACKNOLEDGED.getCode(), OrderStateType.Value.ACKNOLEDGED.name());
//		orderStateMap.put(OrderStateType.Value.ALLOCATED.getCode(), OrderStateType.Value.ALLOCATED.name());
//		orderStateMap.put(OrderStateType.Value.APPROVED.getCode(), OrderStateType.Value.APPROVED.name());
//		orderStateMap.put(OrderStateType.Value.BUILT.getCode(), OrderStateType.Value.BUILT.name());
//		orderStateMap.put(OrderStateType.Value.ORDERED.getCode(), OrderStateType.Value.ORDERED.name());
//		orderStateMap.put(OrderStateType.Value.SCHEDULED.getCode(), OrderStateType.Value.SCHEDULED.name());
//		orderStateMap.put(OrderStateType.Value.SHIPPED.getCode(), OrderStateType.Value.SHIPPED.name());
//		orderStateMap.put(OrderStateType.Value.SUBMITTED.getCode(), OrderStateType.Value.SUBMITTED.name());
//		orderStateMap.put(OrderStateType.Value.MANAGER_APPROVAL_PENDING.getCode(), OrderStateType.Value.MANAGER_APPROVAL_PENDING.name());
//		orderStateMap.put(OrderStateType.Value.MANAGER_APPROVAL_REJECTED.getCode(), OrderStateType.Value.MANAGER_APPROVAL_REJECTED.name());
//		orderStateMap.put(OrderStateType.Value.PENDING_ASSET_SETUP.getCode(), OrderStateType.Value.PENDING_ASSET_SETUP.name());
//		orderStateMap.put(OrderStateType.Value.SAVED.getCode(), OrderStateType.Value.SAVED.name());
//		orderStateMap.put(OrderStateType.Value.AT_UPFITTER.getCode(), OrderStateType.Value.AT_UPFITTER.name());
//		orderStateMap.put(OrderStateType.Value.CANCELLED.getCode(), OrderStateType.Value.CANCELLED.name());
//		orderStateMap.put(OrderStateType.Value.DELIVERED_TO_DRIVER.getCode(), OrderStateType.Value.DELIVERED_TO_DRIVER.name());
//		orderStateMap.put(OrderStateType.Value.AT_DEALER.getCode(), OrderStateType.Value.AT_DEALER.name());
//		orderStateMap.put(OrderStateType.Value.ON_ROAD.getCode(), OrderStateType.Value.ON_ROAD.name());
//	}

	public static CommonOrderEvent createNewEvent(Map<String, Object> orderData) {
//		LOGGER.info("@@@@@ DriverChangeEventFactory started...");
		String event = (String) orderData.get("event");
//		LOGGER.info("@@@@@ event -> {}", event);
//		if (StringUtils.isEmpty(event)) {
//			throw new OrderingException(ORDER_SERVICE, INVALID_EVENT);
//		}
		EventFactory factory = factoryMap.get(event.toUpperCase());
//		if (factory == null) {
//			throw new OrderingException(ORDER_SERVICE, INVALID_EVENT_FACTORY);
//		}
//		LOGGER.info("@@@@@ DriverChangeEventFactory completed...");
		return factory.create(orderData);
	}
	
	// building event from orderState and orderType
//	public static String buildDriverChangeEvent(String orderType, String orderState, Boolean doeIndicator) {
//		if (StringUtils.equals(orderState, OrderStateType.Value.PENDING_ASSET_SETUP.getCode())) {
//			return SAVED_PENDING_ASSET_SETUP_STATE_EVENT.toString();
//		}
//		if (StringUtils.isEmpty((StringUtils.trim(orderType)))
//				|| StringUtils.equals(orderState, OrderStateType.Value.SAVED.getCode())
//				|| StringUtils.equals(orderState, OrderStateType.Value.MANAGER_APPROVAL_PENDING.getCode())
//				|| StringUtils.equals(orderState, OrderStateType.Value.MANAGER_APPROVAL_REJECTED.getCode())) {
//			return StringUtils.join(orderStateMap.get(orderState), "_STATE_EVENT");
//		} else if (StringUtils.equalsAny(orderType, OrderType.Value.STOCK_ORDER.getCode(),
//				OrderType.Value.DEALER_ORDER.getCode())) {
//			return STOCK_AND_DEALER_ORDER_SUBMITTED_STATE_EVENT.toString();
//		} else if (StringUtils.equals(orderType, OrderType.Value.FACTORY_ORDER.getCode())
//				&& !StringUtils.isEmpty(orderState)) {
//			if((Boolean.TRUE.equals(doeIndicator)) && (StringUtils.equals(orderState, OrderStateType.Value.APPROVED.getCode()))) {
//				return AFTER_APPROVAL_BEFORE_TRANSMISSION.toString();
//			}
//			else if ((StringUtils.equalsAny(orderState, OrderStateType.Value.ORDERED.getCode(), OrderStateType.Value.ACKNOLEDGED.getCode(),
//					OrderStateType.Value.ALLOCATED.getCode(), OrderStateType.Value.SCHEDULED.getCode(),
//					OrderStateType.Value.BUILT.getCode(), OrderStateType.Value.SHIPPED.getCode(), OrderStateType.Value.AT_UPFITTER.getCode(),
//					OrderStateType.Value.CANCELLED.getCode(), OrderStateType.Value.AT_DEALER.getCode(),
//					OrderStateType.Value.ON_ROAD.getCode()))) {
//				return AFTER_APPROVAL_AFTER_TRANSMISSION.toString();
//			}
//			return StringUtils.join(OrderType.Value.FACTORY_ORDER.toString(), "_", orderStateMap.get(orderState),
//					"_STATE_EVENT");
//		}
//		return StringUtils.EMPTY;
//	}
}
