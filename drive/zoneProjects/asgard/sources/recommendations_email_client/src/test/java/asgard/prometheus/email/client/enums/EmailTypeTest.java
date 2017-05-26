package asgard.prometheus.email.client.enums;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertEquals;

/**
 * Unit tests to verify whether EmailType enum contains all possible values
 *
 */
public class EmailTypeTest {
    /**
     * EmailType enum should contain all possible values
     *
     */
    @Test
    public void should_contain_all_possible_values_in_EmailType_enum() {
        assertThat(EmailType.valueOf("KOHLS_CASH_GENERIC"), is(notNullValue()));
        assertThat(EmailType.valueOf("PARTIAL_SHIPMENT"), is(notNullValue()));
        assertThat(EmailType.valueOf("COMPLETE_SHIPMENT"), is(notNullValue()));
        assertThat(EmailType.valueOf("BOPUS_ORDER_DELAY_SINGLE_STORE"), is(notNullValue()));
        assertThat(EmailType.valueOf("BOPUS_ORDER_DELAY_MULTI_STORE"), is(notNullValue()));
        assertThat(EmailType.valueOf("BOPUS_ORDER_READY_FOR_PICKUP_SINGLE_STORE"), is(notNullValue()));
        assertThat(EmailType.valueOf("BOPUS_ORDER_READY_FOR_PICKUP_WITH_GIFT_SINGLE_STORE"), is(notNullValue()));
        assertThat(EmailType.valueOf("BOPUS_ORDER_READY_FOR_PICKUP_MULTI_STORE"), is(notNullValue()));
        assertThat(EmailType.valueOf("BOPUS_ORDER_READY_FOR_PICKUP_REMINDER_SINGLE_STORE"), is(notNullValue()));
        assertThat(EmailType.valueOf("BOPUS_ORDER_READY_FOR_PICKUP_REMINDER_MULTI_STORE"), is(notNullValue()));
        assertThat(EmailType.valueOf("BOPUS_FINAL_ORDER_READY_FOR_PICKUP_REMINDER_SINGLE_STORE"), is(notNullValue()));
        assertThat(EmailType.valueOf("BOPUS_FINAL_ORDER_READY_FOR_PICKUP_REMINDER_MULTI_STORE"), is(notNullValue()));
        assertThat(EmailType.valueOf("BOPUS_PICKED_UP_CONFIRMATION_SINGLE_STORE"), is(notNullValue()));
        assertThat(EmailType.valueOf("BOPUS_PICKED_UP_CONFIRMATION_MULTI_STORE"), is(notNullValue()));
        assertThat(EmailType.valueOf("BOPUS_AUTO_REFUND_SINGLE_STORE"), is(notNullValue()));
        assertThat(EmailType.valueOf("BOPUS_AUTO_REFUND_MULTIPLE_STORE"), is(notNullValue()));
        assertThat(EmailType.valueOf("BOPUS_ORDER_MODIFICATION_SINGLE_STORE"), is(notNullValue()));
        assertThat(EmailType.valueOf("BOPUS_ORDER_MODIFICATION_MULTI_STORE"), is(notNullValue()));
        assertThat(EmailType.valueOf("BOPUS_ORDER_CANCELLATION"), is(notNullValue()));
        assertThat(EmailType.valueOf("BOPUS_PICKUP_PERSON_CHANGED"), is(notNullValue()));
        assertThat(EmailType.valueOf("BOPUS_PICKUP_PERSON_FINAL_PICKUP_REMINDER"), is(notNullValue()));
        assertThat(EmailType.valueOf("BOPUS_PICKUP_PERSON_ORDER_READY_FOR_PICKUP"), is(notNullValue()));
        assertThat(EmailType.valueOf("BOPUS_PICKUP_PERSON_PICKEDUP_CONFIRMATION"), is(notNullValue()));
        assertThat(EmailType.valueOf("BOPUS_PICKUP_PERSON_PICKUP_EXPIRED"), is(notNullValue()));
        assertThat(EmailType.valueOf("BOPUS_PICKUP_PERSON_PICKUP_REMINDER"), is(notNullValue()));
    }

    /**
     * All enum constants string representations must be correct
     *
     */
    @Test
    public void should_return_proper_string_representation_of_enum_constants() {
        assertEquals("KohlsCashGeneric", EmailType.KOHLS_CASH_GENERIC.toString());
        assertEquals("PartialShipment", EmailType.PARTIAL_SHIPMENT.toString());
        assertEquals("CompleteShipment", EmailType.COMPLETE_SHIPMENT.toString());
        assertEquals("Bopus1StoreOrderDelay", EmailType.BOPUS_ORDER_DELAY_SINGLE_STORE.toString());
        assertEquals("BopusMStoreOrderDelay", EmailType.BOPUS_ORDER_DELAY_MULTI_STORE.toString());
        assertEquals("Bopus1StoreOrderReadyForPickup", EmailType.BOPUS_ORDER_READY_FOR_PICKUP_SINGLE_STORE.toString());
        assertEquals("Bopus1StoreOrderReadyForPickupWithGift", EmailType.BOPUS_ORDER_READY_FOR_PICKUP_WITH_GIFT_SINGLE_STORE.toString());
        assertEquals("BopusMStoreOrderReadyForPickup", EmailType.BOPUS_ORDER_READY_FOR_PICKUP_MULTI_STORE.toString());
        assertEquals("Bopus1StoreOrderReadyForPickupReminder", EmailType.BOPUS_ORDER_READY_FOR_PICKUP_REMINDER_SINGLE_STORE.toString());
        assertEquals("BopusMStoreOrderReadyForPickupReminder", EmailType.BOPUS_ORDER_READY_FOR_PICKUP_REMINDER_MULTI_STORE.toString());
        assertEquals("Bopus1StoreFinalOrderReadyForPickupReminder", EmailType.BOPUS_FINAL_ORDER_READY_FOR_PICKUP_REMINDER_SINGLE_STORE.toString());
        assertEquals("BopusMStoreFinalOrderReadyForPickupReminder", EmailType.BOPUS_FINAL_ORDER_READY_FOR_PICKUP_REMINDER_MULTI_STORE.toString());
        assertEquals("Bopus1StorePickedUpConfirmation", EmailType.BOPUS_PICKED_UP_CONFIRMATION_SINGLE_STORE.toString());
        assertEquals("BopusMStorePickedUpConfirmation", EmailType.BOPUS_PICKED_UP_CONFIRMATION_MULTI_STORE.toString());
        assertEquals("Bopus1StoreAutoRefund", EmailType.BOPUS_AUTO_REFUND_SINGLE_STORE.toString());
        assertEquals("BopusMStoreAutoRefund", EmailType.BOPUS_AUTO_REFUND_MULTIPLE_STORE.toString());
        assertEquals("Bopus1StoreOrderModification", EmailType.BOPUS_ORDER_MODIFICATION_SINGLE_STORE.toString());
        assertEquals("BopusMStoreOrderModification", EmailType.BOPUS_ORDER_MODIFICATION_MULTI_STORE.toString());
        assertEquals("BopusOrderCancellation", EmailType.BOPUS_ORDER_CANCELLATION.toString());
        assertEquals("BopusPickupPersonChanged", EmailType.BOPUS_PICKUP_PERSON_CHANGED.toString());
        assertEquals("BopusPickupPersonFinalPickupReminder", EmailType.BOPUS_PICKUP_PERSON_FINAL_PICKUP_REMINDER.toString());
        assertEquals("BopusPickupPersonOrderReadyForPickup", EmailType.BOPUS_PICKUP_PERSON_ORDER_READY_FOR_PICKUP.toString());
        assertEquals("BopusPickupPersonPickedUpConfirmation", EmailType.BOPUS_PICKUP_PERSON_PICKEDUP_CONFIRMATION.toString());
        assertEquals("BopusPickupPersonPickupExpired", EmailType.BOPUS_PICKUP_PERSON_PICKUP_EXPIRED.toString());
        assertEquals("BopusPickupPersonPickupReminder", EmailType.BOPUS_PICKUP_PERSON_PICKUP_REMINDER.toString());
    }
}

