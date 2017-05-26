package asgard.prometheus.email.client.enums;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertEquals;

/**
 * Unit tests to verify whether QueryParamName enum contains all possible values
 *
 */
public class QueryParamNameTest {
    /**
     * QueryParamName enum should contain all possible values
     *
     */
    @Test
    public void should_contain_all_possible_values_in_QueryParamName_enum() {
        assertThat(QueryParamName.valueOf("CHANNEL_ID"), is(notNullValue()));
        assertThat(QueryParamName.valueOf("EMAIL_TYPE"), is(notNullValue()));
        assertThat(QueryParamName.valueOf("PLACEMENT_ID"), is(notNullValue()));
        assertThat(QueryParamName.valueOf("POSITION"), is(notNullValue()));
        assertThat(QueryParamName.valueOf("CCP"), is(notNullValue()));
    }

    /**
     * All enum constants string representations must be correct
     *
     */
    @Test
    public void should_return_proper_string_representation_of_enum_constants() {
        assertEquals("cid", QueryParamName.CHANNEL_ID.toString());
        assertEquals("etype", QueryParamName.EMAIL_TYPE.toString());
        assertEquals("plid", QueryParamName.PLACEMENT_ID.toString());
        assertEquals("pos", QueryParamName.POSITION.toString());
        assertEquals("ccp", QueryParamName.CCP.toString());
    }
}

