package asgard.prometheus.email.client.utilities;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.Appender;
import asgard.prometheus.email.client.enums.ChannelContextParamName;
import org.apache.commons.codec.binary.Base64;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

/**
 * Unit tests to verify the functionality of the ChannelContextParamUtilities class.
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ChannelContextParamUtilities.class, Base64.class})
public class ChannelContextParamUtilitiesTest {
    //Mock appender for capturing log messages
    @Mock
    private Appender mockAppender;

    //Captor for logging events
    @Captor
    private ArgumentCaptor<LoggingEvent> captorLoggingEvent;

    /**
     * This method executes before any test.
     *
     */
    @Before
    public void setup() {
        //Inject the mock log appender
        final Logger logger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        logger.addAppender(mockAppender);
    }

    /**
     * This method executes after any test.
     *
     */
    @After
    public void teardown() {
        //Release the mock log appender
        final Logger logger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        logger.detachAppender(mockAppender);
    }

    /**
     * If invoked, the private constructor should create and instance of the ChannelContextParamUtilities class.
     *
     */
    @Test
    public void should_create_an_instance_if_the_private_constructor_is_invoked() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<ChannelContextParamUtilities> constructor = ChannelContextParamUtilities.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        ChannelContextParamUtilities channelContextParamUtilities = constructor.newInstance();
        assertNotNull(channelContextParamUtilities);
    }

    /**
     * null should be returned when tried to encode an empty channel context parameter map.
     *
     */
    @Test
    public void should_return_null_when_trying_to_encode_an_empty_ccp_map() {
        assertNull(ChannelContextParamUtilities.encodeCcpMapToUrlFormat(new HashMap<String, String>()));
    }

    /**
     * null should be returned when tried to encode passing a null as the input for the channel context
     * parameter map.
     *
     */
    @Test
    public void should_return_null_when_trying_to_encode_null_passed_in_as_ccp_map() {
        assertNull(ChannelContextParamUtilities.encodeCcpMapToUrlFormat(null));
    }

    /**
     * Proper ccp map should be encoded to the expected encoded ccp string.
     *
     */
    @Test
    public void should_properly_encode_a_given_non_empty_ccp_map() {
        String expectedEncodedCcpString = "eyJrb2hsc0Nhc2hVcHBlckxpbWl0IjoiMTAuNTAiLCJrb2hsc0Nhc2hMb3dlckxpbWl0IjoiNS4wMCIsImN1c3RvbWVyRW1haWxIYXNoIjoiYWJjQGdtYWlsLmNvbSIsImtvaGxzQ2FzaEFtb3VudCI6IjI1LjUwIn0";

        Map<String, String> ccp = new HashMap<>();
        ccp.put(ChannelContextParamName.KOHLS_CASH_AMOUNT.toString(), "25.50");
        ccp.put(ChannelContextParamName.KOHLS_CASH_LOWER_LIMIT.toString(), "5.00");
        ccp.put(ChannelContextParamName.KOHLS_CASH_UPPER_LIMIT.toString(), "10.50");
        ccp.put(ChannelContextParamName.CUSTOMER_EMAIL_HASH.toString(), "abc@gmail.com");

        String actualEncodedCcpString = ChannelContextParamUtilities.encodeCcpMapToUrlFormat(ccp);

        assertEquals(expectedEncodedCcpString, actualEncodedCcpString);
    }

    /**
     * Proper error message should be logged if serializing the ccp map threw any exceptions.
     *
     */
    @Test
    public void should_log_proper_error_msg_if_serialization_threw_exceptions_when_encoding_ccp() throws Exception {
        mockStatic(Base64.class);
        Mockito.when(Base64.encodeBase64URLSafeString(any(byte[].class))).thenThrow(UnsupportedEncodingException.class);

        Map<String, String> ccp = new HashMap<>();
        ccp.put(ChannelContextParamName.KOHLS_CASH_AMOUNT.toString(), "25.50");
        ccp.put(ChannelContextParamName.KOHLS_CASH_LOWER_LIMIT.toString(), "5.00");
        ccp.put(ChannelContextParamName.KOHLS_CASH_UPPER_LIMIT.toString(), "10.50");
        ccp.put(ChannelContextParamName.CUSTOMER_EMAIL_HASH.toString(), "abc@gmail.com");

        String actualEncodedCcpString = ChannelContextParamUtilities.encodeCcpMapToUrlFormat(ccp);

        verifyStatic();
        Base64.encodeBase64URLSafeString(any(byte[].class));

        verify(mockAppender).doAppend(captorLoggingEvent.capture());
        final LoggingEvent loggingEvent = captorLoggingEvent.getValue();
        assertThat(loggingEvent.getLevel(), is(Level.ERROR));
        assertThat(loggingEvent.getFormattedMessage(), is("Error in serializing Map to Base64"));
    }

    /**
     * null should be returned if a null customer email address is hashed.
     *
     */
    @Test
    public void should_return_null_when_trying_to_hash_a_null_customer_email_address() {
        String hashedEmailAddress = ChannelContextParamUtilities.hashCustomerEmailAddress(null);
        assertNull(hashedEmailAddress);
    }

    /**
     * null should be returned if an empty string email address is hashed.
     *
     */
    @Test
    public void should_return_null_when_trying_to_hash_an_empty_string_email_address() {
        String hashedEmailAddress = ChannelContextParamUtilities.hashCustomerEmailAddress("");
        assertNull(hashedEmailAddress);
    }

    /**
     * When a non-null and non-empty email address is provided, it should be hashed properly.
     *
     */
    @Test
    public void should_properly_hash_non_empty_email_address() {
        String expectedHashedEmailAddress = "1524547429279238715";
        String hashedEmailAddress = ChannelContextParamUtilities.hashCustomerEmailAddress("abc@gmail.com");
        assertEquals(expectedHashedEmailAddress, hashedEmailAddress);
    }
}

