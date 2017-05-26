package asgard.prometheus.email.client.enums;

/**
 * Supported Email Types.
 */
public enum EmailType {
    /**
     * Generic Kohl's cash email.
     */
    KOHLS_CASH_GENERIC("KohlsCashGeneric"),

    /**
     * Partial shipment email.
     */
    PARTIAL_SHIPMENT("PartialShipment"),

    /**
     * Complete shipment email.
     */
    COMPLETE_SHIPMENT("CompleteShipment"),

    /**
     * BOPUS: Order Delay
     */
    BOPUS_ORDER_DELAY_SINGLE_STORE("Bopus1StoreOrderDelay"),

    /**
     * BOPUS: Order Delay - Multiple Stores
     */
    BOPUS_ORDER_DELAY_MULTI_STORE("BopusMStoreOrderDelay"),

    /**
     * BOPUS: Order Ready For Pickup
     */
    BOPUS_ORDER_READY_FOR_PICKUP_SINGLE_STORE("Bopus1StoreOrderReadyForPickup"),

    /**
     * BOPUS: Order Ready For Pickup - With Gift
     */
    BOPUS_ORDER_READY_FOR_PICKUP_WITH_GIFT_SINGLE_STORE("Bopus1StoreOrderReadyForPickupWithGift"),

    /**
     * BOPUS: Order Ready For Pickup - Multiple Stores
     */
    BOPUS_ORDER_READY_FOR_PICKUP_MULTI_STORE("BopusMStoreOrderReadyForPickup"),

    /**
     * BOPUS: Order Ready For Pickup Reminder
     */
    BOPUS_ORDER_READY_FOR_PICKUP_REMINDER_SINGLE_STORE("Bopus1StoreOrderReadyForPickupReminder"),

    /**
     * BOPUS: Order Ready For Pickup Reminder - Multiple Stores
     */
    BOPUS_ORDER_READY_FOR_PICKUP_REMINDER_MULTI_STORE("BopusMStoreOrderReadyForPickupReminder"),

    /**
     * BOPUS: Final Order Ready For Pickup Reminder
     */
    BOPUS_FINAL_ORDER_READY_FOR_PICKUP_REMINDER_SINGLE_STORE("Bopus1StoreFinalOrderReadyForPickupReminder"),

    /**
     * BOPUS: Final Order Ready For Pickup Reminder - Multiple Stores
     */
    BOPUS_FINAL_ORDER_READY_FOR_PICKUP_REMINDER_MULTI_STORE("BopusMStoreFinalOrderReadyForPickupReminder"),

    /**
     * BOPUS: Picked Up Confirmation
     */
    BOPUS_PICKED_UP_CONFIRMATION_SINGLE_STORE("Bopus1StorePickedUpConfirmation"),

    /**
     * BOPUS: Picked Up Confirmation - Multiple Stores
     */
    BOPUS_PICKED_UP_CONFIRMATION_MULTI_STORE("BopusMStorePickedUpConfirmation"),

    /**
     * BOPUS: Auto Refund
     */
    BOPUS_AUTO_REFUND_SINGLE_STORE("Bopus1StoreAutoRefund"),

    /**
     * BOPUS: Auto Refund - Multiple Stores
     */
    BOPUS_AUTO_REFUND_MULTIPLE_STORE("BopusMStoreAutoRefund"),

    /**
     * BOPUS: Order Modification
     */
    BOPUS_ORDER_MODIFICATION_SINGLE_STORE("Bopus1StoreOrderModification"),

    /**
     * BOPUS: Order Modification - Multiple Stores
     */
    BOPUS_ORDER_MODIFICATION_MULTI_STORE("BopusMStoreOrderModification"),

    /**
     * BOPUS: Order Cancellation
     */
    BOPUS_ORDER_CANCELLATION("BopusOrderCancellation"),

    /**
     * Pickup person changed to another pickup person
     */
    BOPUS_PICKUP_PERSON_CHANGED("BopusPickupPersonChanged"),

    /**
     * Pickup person final pickup reminder
     */
    BOPUS_PICKUP_PERSON_FINAL_PICKUP_REMINDER("BopusPickupPersonFinalPickupReminder"),

    /**
     * Pickup person order ready for pickup
     */
    BOPUS_PICKUP_PERSON_ORDER_READY_FOR_PICKUP("BopusPickupPersonOrderReadyForPickup"),

    /**
     * Pickup person picked up confirmation
     */
    BOPUS_PICKUP_PERSON_PICKEDUP_CONFIRMATION("BopusPickupPersonPickedUpConfirmation"),

    /**
     * Pickup person pickup expired
     */
    BOPUS_PICKUP_PERSON_PICKUP_EXPIRED("BopusPickupPersonPickupExpired"),

    /**
     * Pickup person pickup reminder
     */
    BOPUS_PICKUP_PERSON_PICKUP_REMINDER("BopusPickupPersonPickupReminder");

    private final String emailTypeStrValue;

    private EmailType(final String emailTypeStrValue) {
        this.emailTypeStrValue = emailTypeStrValue;
    }

    /**
     * Provides the string representation of the enum constants.
     *
     * @return  The string representation of the given enum constant.
     */
    @Override
    public String toString() {
        return emailTypeStrValue;
    }
}

