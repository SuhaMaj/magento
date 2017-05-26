package asgard.prometheus.email.client.utilities;

import asgard.prometheus.email.client.enums.ChannelId;
import asgard.prometheus.email.client.enums.EmailType;
import asgard.prometheus.email.client.enums.PlacementId;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;


/**
 * Unit tests to verify the functionality of the ParameterValidatorUtilities class
 *
 */
public class ParameterValidatorUtilitiesTest {
    /**
     * If invoked, the private constructor should create and instance of the ParameterValidatorUtilities class.
     *
     */
    @Test
    public void should_create_an_instance_if_the_private_constructor_is_invoked() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<ParameterValidatorUtilities> constructor = ParameterValidatorUtilities.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        ParameterValidatorUtilities parameterValidatorUtilities = constructor.newInstance();
        assertNotNull(parameterValidatorUtilities);
    }
    /**
     * http and https protocols must be validated properly
     *
     */
    @Test
    public void should_return_true_for_http_and_https_when_protocol_is_validated_for_http_and_https() {
        assertTrue(ParameterValidatorUtilities.validateProtocolForHttpAndHttps("http"));
        assertTrue(ParameterValidatorUtilities.validateProtocolForHttpAndHttps("https"));
        assertTrue(ParameterValidatorUtilities.validateProtocolForHttpAndHttps("Http"));
        assertTrue(ParameterValidatorUtilities.validateProtocolForHttpAndHttps("HTTPS"));
    }

    /**
     * null input should return false when validated for proper protocol
     *
     */
    @Test
    public void should_return_false_for_null_input_when_protocol_is_validated_for_http_and_https() {
        assertFalse(ParameterValidatorUtilities.validateProtocolForHttpAndHttps(null));
    }

    /**
     * Empty string input should return false when validated for proper protocol
     *
     */
    @Test
    public void should_return_false_for_empty_string_input_protocol_is_validated_for_http_and_https() {
        assertFalse(ParameterValidatorUtilities.validateProtocolForHttpAndHttps(""));
    }

    /**
     * A valid hostname should return true when validated for proper hostname
     *
     */
    @Test
    public void should_return_true_for_valid_hostname_when_hostname_is_validated() {
        assertTrue(ParameterValidatorUtilities.validateHostnameForNonNullAndNonEmpty("api-atg.kohls.com"));
    }

    /**
     * null input should return false when validated for proper hostname
     *
     */
    @Test
    public void should_return_false_for_null_input_when_hostname_is_validated() {
        assertFalse(ParameterValidatorUtilities.validateHostnameForNonNullAndNonEmpty(null));
    }

    /**
     * Empty string input should return false when validated for proper hostname
     *
     */
    @Test
    public void should_return_false_for_empty_string_input_when_hostname_is_validated() {
        assertFalse(ParameterValidatorUtilities.validateHostnameForNonNullAndNonEmpty(""));
    }

    /**
     * Valid ports in the correct range should validate properly
     *
     */
    @Test
    public void should_return_true_for_valid_ports_when_port_is_validated() {
        assertTrue(ParameterValidatorUtilities.validatePortForValidRange(0));
        assertTrue(ParameterValidatorUtilities.validatePortForValidRange(65535));
        assertTrue(ParameterValidatorUtilities.validatePortForValidRange(443));
    }

    /**
     * Invalid ports should result in retuning false for port validation
     *
     */
    @Test
    public void should_return_false_for_ports_that_are_out_of_range() {
        assertFalse(ParameterValidatorUtilities.validatePortForValidRange(65536));
        assertFalse(ParameterValidatorUtilities.validatePortForValidRange(-1));
        assertFalse(ParameterValidatorUtilities.validatePortForValidRange(-100));
        assertFalse(ParameterValidatorUtilities.validatePortForValidRange(70000));
    }

    /**
     * Valid non-negative positions should return true when the position is validated
     *
     */
    @Test
    public void should_return_true_for_non_negative_positions_when_position_is_validated() {
        assertTrue(ParameterValidatorUtilities.validateRecoPositionForPositivity(1));
        assertTrue(ParameterValidatorUtilities.validateRecoPositionForPositivity(4));
        assertTrue(ParameterValidatorUtilities.validateRecoPositionForPositivity(0));
    }

    /**
     * Negative positions should return false when validated for proper position
     *
     */
    @Test
    public void should_return_false_for_negative_positions_when_position_is_validated() {
        assertFalse(ParameterValidatorUtilities.validateRecoPositionForPositivity(-1));
        assertFalse(ParameterValidatorUtilities.validateRecoPositionForPositivity(-4));
    }

    /**
     * null positions should return false when validated for proper position
     *
     */
    @Test
    public void should_return_false_for_null_positions_when_position_is_validated() {
        assertFalse(ParameterValidatorUtilities.validateRecoPositionForPositivity(null));
    }

    /**
     * null channel id should return a false when validated for a valid channel id.
     */
    @Test
    public void should_return_false_when_channel_id_is_null() {
        assertFalse(ParameterValidatorUtilities.validateChannelIdForNonNull(null));
    }

    /**
     * Valid channel id should return true when validated for a valid channel id.
     */
    @Test
    public void should_return_true_when_a_valid_channel_id_is_provided() {
        assertTrue(ParameterValidatorUtilities.validateChannelIdForNonNull(ChannelId.MKT_EMAIL));
    }

    /**
     * null email type should return a false when validated for a valid email type.
     */
    @Test
    public void should_return_false_when_email_type_is_null() {
        assertFalse(ParameterValidatorUtilities.validateEmailTypeForNonNull(null));
    }

    /**
     * Valid channel id should return true when validated for a valid email type.
     */
    @Test
    public void should_return_true_when_a_valid_email_type_is_provided() {
        assertTrue(ParameterValidatorUtilities.validateEmailTypeForNonNull(EmailType.KOHLS_CASH_GENERIC));
    }

    /**
     * null placement id should return a false when validated for a valid placement id.
     */
    @Test
    public void should_return_false_when_placement_id_is_null() {
        assertFalse(ParameterValidatorUtilities.validatePlacementIdForNonNull(null));
    }

    /**
     * Valid placement id should return true when validated for a valid placement id.
     */
    @Test
    public void should_return_true_when_a_valid_placement_id_is_provided() {
        assertTrue(ParameterValidatorUtilities.validatePlacementIdForNonNull(PlacementId.HORIZONTAL));
    }

    /**
     * null email recommendation base URLs should return false when validated.
     */
    @Test
    public void should_return_false_for_null_email_rec_base_url() {
        assertFalse(ParameterValidatorUtilities.validateEmailRecoBaseUrlForNonNullAndNonEmpty(null));
    }

    /**
     * Empty string should return false when validated for valid email recommendation base URL.
     */
    @Test
    public void should_return_false_for_empty_email_rec_base_url() {
        assertFalse(ParameterValidatorUtilities.validateEmailRecoBaseUrlForNonNullAndNonEmpty(""));
    }

    /**
     * Some valid URL string should return true when validate for a valid email recommendation base URL.
     */
    @Test
    public void should_return_true_when_email_recommendation_base_url_is_non_null_and_non_empty() {
        assertTrue(ParameterValidatorUtilities.validateEmailRecoBaseUrlForNonNullAndNonEmpty("Some URL"));
    }

    /**
     * Null product number list should return false.
     */
    @Test
    public void should_return_false_when_product_number_list_is_null() {
        assertFalse(ParameterValidatorUtilities.validateProductNumberListForNonNullAndNonEmpty(null));
    }

    /**
     * Empty product number list should return false.
     */
    @Test
    public void should_return_false_when_product_number_list_is_empty() {
        assertFalse(ParameterValidatorUtilities.validateProductNumberListForNonNullAndNonEmpty(new LinkedList<String>()));
    }

    /**
     * Non empty product number list should return true.
     */
    @Test
    public void should_return_true_when_product_number_list_is_not_empty() {
        List<String> productNumbers = new LinkedList<>();
        productNumbers.add("12345");
        productNumbers.add("45678");
        assertTrue(ParameterValidatorUtilities.validateProductNumberListForNonNullAndNonEmpty(productNumbers));
    }

    /**
     * Null store number list should return false.
     */
    @Test
    public void should_return_false_when_store_number_list_is_null() {
        assertFalse(ParameterValidatorUtilities.validateStoreNumberListForNonNullAndNonEmpty(null));
    }

    /**
     * Empty store number list should return false.
     */
    @Test
    public void should_return_false_when_store_number_list_is_empty() {
        assertFalse(ParameterValidatorUtilities.validateStoreNumberListForNonNullAndNonEmpty(new LinkedList<String>()));
    }

    /**
     * Non empty store number list should return true.
     */
    @Test
    public void should_return_true_when_store_number_list_is_not_empty() {
        List<String> storeNumbers = new LinkedList<>();
        storeNumbers.add("12345");
        storeNumbers.add("45678");
        assertTrue(ParameterValidatorUtilities.validateStoreNumberListForNonNullAndNonEmpty(storeNumbers));
    }

    /**
     * Null customer email address should return false.
     */
    @Test
    public void should_return_false_when_customer_email_address_is_null() {
        assertFalse(ParameterValidatorUtilities.validateCustomerEmailAddressForNonNullAndNonEmpty(null));
    }

    /**
     * Empty customer email address should return false.
     */
    @Test
    public void should_return_false_when_customer_email_address_is_empty() {
        assertFalse(ParameterValidatorUtilities.validateCustomerEmailAddressForNonNullAndNonEmpty(""));
    }

    /**
     * Non empty customer email address should return true.
     */
    @Test
    public void should_return_true_when_customer_email_address_is_not_empty() {
        assertTrue(ParameterValidatorUtilities.validateCustomerEmailAddressForNonNullAndNonEmpty("bathiya@kohls.com"));
    }
}

