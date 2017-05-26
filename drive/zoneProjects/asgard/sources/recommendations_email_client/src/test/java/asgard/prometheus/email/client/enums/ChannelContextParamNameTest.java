package asgard.prometheus.email.client.enums;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertEquals;

/**
 * Unit tests to verify whether ChannelContextParamName enum contains all possible values
 *
 */
public class ChannelContextParamNameTest {
    /**
     * ChannelContextParamName enum should contain all possible values
     *
     */
    @Test
    public void should_contain_all_possible_values_in_ChannelContextParamName_enum() {
        assertThat(ChannelContextParamName.valueOf("KOHLS_CASH_AMOUNT"), is(notNullValue()));
        assertThat(ChannelContextParamName.valueOf("KOHLS_CASH_LOWER_LIMIT"), is(notNullValue()));
        assertThat(ChannelContextParamName.valueOf("KOHLS_CASH_UPPER_LIMIT"), is(notNullValue()));
        assertThat(ChannelContextParamName.valueOf("CUSTOMER_EMAIL_HASH"), is(notNullValue()));
        assertThat(ChannelContextParamName.valueOf("PRODUCT_NUMBERS"), is(notNullValue()));
        assertThat(ChannelContextParamName.valueOf("STORE_NUMBERS"), is(notNullValue()));
        assertThat(ChannelContextParamName.valueOf("ORDER_NUMBER"), is(notNullValue()));
        assertThat(ChannelContextParamName.valueOf("ATG_ID"), is(notNullValue()));
    }

    /**
     * All enum constants string representations must be correct
     *
     */
    @Test
    public void should_return_proper_string_representation_of_enum_constants() {
        assertEquals("kohlsCashAmount", ChannelContextParamName.KOHLS_CASH_AMOUNT.toString());
        assertEquals("kohlsCashLowerLimit", ChannelContextParamName.KOHLS_CASH_LOWER_LIMIT.toString());
        assertEquals("kohlsCashUpperLimit", ChannelContextParamName.KOHLS_CASH_UPPER_LIMIT.toString());
        assertEquals("customerEmailHash", ChannelContextParamName.CUSTOMER_EMAIL_HASH.toString());
        assertEquals("productNumbers", ChannelContextParamName.PRODUCT_NUMBERS.toString());
        assertEquals("shipNodes", ChannelContextParamName.STORE_NUMBERS.toString());
        assertEquals("orderNumber", ChannelContextParamName.ORDER_NUMBER.toString());
        assertEquals("atgId", ChannelContextParamName.ATG_ID.toString());
    }
}
