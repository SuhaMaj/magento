package asgard.prometheus.email.client.enums;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;

/**
 * Unit tests to verify whether ChannelId enum contains all possible values
 *
 */
public class ChannelIdTest {
    /**
     * ChannelId enumeration should contain all possible values
     *
     */
    @Test
    public void should_contain_all_possible_enum_values_in_ChannelId_enum() {
        assertThat(ChannelId.valueOf("MKT_EMAIL"), is(notNullValue()));
        assertThat(ChannelId.valueOf("TRANS_EMAIL"), is(notNullValue()));
        assertThat(ChannelId.valueOf("DEMO_EMAIL"), is(notNullValue()));
    }

    /**
     * All enum constants string representations must be correct
     *
     */
    @Test
    public void should_return_proper_string_representation_of_enum_constants() {
        assertEquals("MktEmail", ChannelId.MKT_EMAIL.toString());
        assertEquals("TransEmail", ChannelId.TRANS_EMAIL.toString());
        assertEquals("DemoEmail", ChannelId.DEMO_EMAIL.toString());
    }
}

