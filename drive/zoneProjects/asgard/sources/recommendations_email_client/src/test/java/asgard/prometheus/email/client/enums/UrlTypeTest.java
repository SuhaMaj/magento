package asgard.prometheus.email.client.enums;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * Unit tests to verify whether UrlType enum contains all possible values
 *
 */
public class UrlTypeTest {
    /**
     * UrlType enum should contain all possible values
     *
     */
    @Test
    public void should_contain_all_possible_values_in_UrlType_enum() {
        assertThat(UrlType.valueOf("EMAIL_SINGLE_IMG_REQ"), is(notNullValue()));
        assertThat(UrlType.valueOf("EMAIL_SINGLE_PROD_REQ"), is(notNullValue()));
    }
}
