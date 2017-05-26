package asgard.prometheus.email.client.utilities;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.Appender;
import asgard.prometheus.email.client.enums.ChannelId;
import asgard.prometheus.email.client.enums.EmailType;
import asgard.prometheus.email.client.enums.PlacementId;
import asgard.prometheus.email.client.enums.QueryParamName;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.net.URLCodec;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mock;

/**
 * Unit tests to verify the functionality of the UrlParameterUtilities class.
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(UrlParameterUtilities.class)
public class UrlParameterUtilitiesTest {
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
     * If invoked, the private constructor should create and instance of the UrlParameterUtilities class.
     *
     */
    @Test
    public void should_create_an_instance_if_the_private_constructor_is_invoked() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<UrlParameterUtilities> constructor = UrlParameterUtilities.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        UrlParameterUtilities urlParameterUtilities = constructor.newInstance();
        assertNotNull(urlParameterUtilities);
    }

    /**
     * An empty string should be returned when null is passed as the input map.
     *
     */
    @Test
    public void should_return_empty_string_if_input_map_is_null() {
        assertEquals("", UrlParameterUtilities.generateQueryParamStringFromParamValueMap(null));
    }

    /**
     * An empty string should be returned when an empty map is passed as the input map.
     *
     */
    @Test
    public void should_return_empty_string_if_input_map_is_empty() {
        assertEquals("", UrlParameterUtilities.generateQueryParamStringFromParamValueMap(new HashMap<String, String>()));
    }

    /**
     * A proper query param map should be properly converted to the expected query param string.
     *
     */
    @Test
    public void should_properly_prepare_the_query_string_when_a_valid_query_param_map_is_inputted() {
        String encodedCcpString = "eyJrb2hsc0Nhc2hMb3dlckxpbWl0IjoiNS4wMCIsImtvaGxzQ2FzaFVwcGVyTGltaXQiOiIxMC41\r\n" +
                "MCIsImN1c3RvbWVyRW1haWwiOiJhYmNAZ21haWwuY29tIiwia29obHNDYXNoQW1vdW50IjoiMjUu\r\n" +
                "NTAi";

        String expectedQueryParamString = "cid=MktEmail" +
                "&etype=KohlsCashGeneric" +
                "&plid=Horizontal" +
                "&pos=1" +
                "&ccp=eyJrb2hsc0Nhc2hMb3dlckxpbWl0IjoiNS4wMCIsImtvaGxzQ2FzaFVwcGVyTGltaXQiOiIxMC41%0D%0AMCIsImN1c3RvbWVyRW1haWwiOiJhYmNAZ21haWwuY29tIiwia29obHNDYXNoQW1vdW50IjoiMjUu%0D%0ANTAi";

        Map<String, String> queryParamValueMap = new LinkedHashMap<>();
        queryParamValueMap.put(QueryParamName.CHANNEL_ID.toString(), ChannelId.MKT_EMAIL.toString());
        queryParamValueMap.put(QueryParamName.EMAIL_TYPE.toString(), EmailType.KOHLS_CASH_GENERIC.toString());
        queryParamValueMap.put(QueryParamName.PLACEMENT_ID.toString(), PlacementId.HORIZONTAL.toString());
        queryParamValueMap.put(QueryParamName.POSITION.toString(), "1");
        queryParamValueMap.put(QueryParamName.CCP.toString(), encodedCcpString);

        String actualQueryParamString = UrlParameterUtilities.generateQueryParamStringFromParamValueMap(queryParamValueMap);

        assertEquals(expectedQueryParamString, actualQueryParamString);

    }

    /**
     * Any empty value parameters in the map should be ignored and excluded from adding to the query parameter string.
     *
     */
    @Test
    public void should_ignore_empty_valued_params_when_generating_the_query_param_string() {
        String expectedQueryParamString = "cid=MktEmail" +
                "&etype=KohlsCashGeneric" +
                "&plid=Horizontal" +
                "&pos=1";

        Map<String, String> queryParamValueMap = new LinkedHashMap<>();
        queryParamValueMap.put(QueryParamName.CHANNEL_ID.toString(), ChannelId.MKT_EMAIL.toString());
        queryParamValueMap.put(QueryParamName.EMAIL_TYPE.toString(), EmailType.KOHLS_CASH_GENERIC.toString());
        queryParamValueMap.put(QueryParamName.PLACEMENT_ID.toString(), PlacementId.HORIZONTAL.toString());
        queryParamValueMap.put(QueryParamName.POSITION.toString(), "1");
        queryParamValueMap.put(QueryParamName.CCP.toString(), "");

        String actualQueryParamString = UrlParameterUtilities.generateQueryParamStringFromParamValueMap(queryParamValueMap);

        assertEquals(expectedQueryParamString, actualQueryParamString);
    }

    /**
     * Proper error message should be logged if the URLCodec threw any exceptions while encoding the given string.
     *
     */
    @Test
    public void should_log_proper_error_message_if_url_codec_threw_an_exception_while_encoding() throws Exception {
        URLCodec urlCodec = mock(URLCodec.class);
        doThrow(EncoderException.class).when(urlCodec).encode(anyString());

        //Set the final static URLCodec with mocked one using reflection
        try {
            setFinalStatic(UrlParameterUtilities.class.getDeclaredField("URLCODEC"), urlCodec);
        }
        catch (SecurityException | NoSuchFieldException e) {
            fail();
        }

        String encodedString = Whitebox.<String> invokeMethod(UrlParameterUtilities.class, "urlEncode", "abc");

        assertNull(encodedString);
        verify(urlCodec, times(1)).encode(anyString());
        verify(mockAppender).doAppend(captorLoggingEvent.capture());
        final LoggingEvent loggingEvent = captorLoggingEvent.getValue();
        assertThat(loggingEvent.getLevel(), is(Level.ERROR));
        assertThat(loggingEvent.getFormattedMessage(), is("Error in encoding Base64 string to URL healthy Base64 string"));

        //Set the final static URLCodec again to an actual URLCodec to keep the state unchanged
        try {
            setFinalStatic(UrlParameterUtilities.class.getDeclaredField("URLCODEC"), new URLCodec());
        }
        catch (SecurityException | NoSuchFieldException e) {
            fail();
        }
    }

    /*
        This method was adapted from the StackOverflow thread at,
        http://stackoverflow.com/questions/23162520/powermock-mock-out-private-static-final-variable-a-concrete-example
     */
    void setFinalStatic(Field field, Object newValue) throws Exception {
        field.setAccessible(true);
        // remove final modifier from field
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        field.set(null, newValue);
        modifiersField.setInt(field, field.getModifiers() & Modifier.FINAL);
        modifiersField.setAccessible(false);
    }
}

