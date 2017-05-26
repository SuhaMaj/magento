package asgard.prometheus.email.client.enums;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertEquals;

/**
 * Unit tests to verify whether PlacementId enum contains all possible values
 *
 */
public class PlacementIdTest {
    /**
     * PlacementId enum should contain all possible values
     *
     */
    @Test
    public void should_contain_all_possible_values_in_PlacementId_enum() {
        assertThat(PlacementId.valueOf("HORIZONTAL"), is(notNullValue()));
    }

    /**
     * All enum constants string representations must be correct
     *
     */
    @Test
    public void should_return_proper_string_representation_of_enum_constants() {
        assertEquals("Horizontal", PlacementId.HORIZONTAL.toString());
    }
}

