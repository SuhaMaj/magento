package asgard.prometheus.email.client.exceptions;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Unit tests to verify the functionality of the InvalidParameterException class
 *
 */
public class InvalidParameterExceptionTest {
    /**
     * Default constructor of the InvalidParameterException class should create an instance properly
     *
     */
    @Test
    public void should_create_exception_object_when_the_default_constructor_is_called() {
        InvalidParameterException invalidParameterException = new InvalidParameterException();
        assertNotNull(invalidParameterException);
    }

    /**
     * Constructor that accepts an exception message of the InvalidParameterException class should create an instance
     * properly
     *
     */
    @Test
    public void should_create_exception_object_with_the_given_message_when_the_exception_message_accepting_constructor_is_called() {
        String exceptionMsg = "My exception message";
        InvalidParameterException invalidParameterException = new InvalidParameterException(exceptionMsg);
        assertNotNull(invalidParameterException);
        assertEquals(exceptionMsg, invalidParameterException.getMessage());
    }

    /**
     * Constructor that accepts a throwable cause of the InvalidParameterException class should create an instance
     * properly
     */
    @Test
    public void should_create_exception_object_with_the_given_cause_when_the_throwable_cause_accepting_constructor_is_called() {
        Throwable mockCause = new Throwable();
        InvalidParameterException invalidParameterException = new InvalidParameterException(mockCause);
        assertNotNull(invalidParameterException);
        assertEquals(mockCause, invalidParameterException.getCause());
    }

    /**
     * Constructor that accepts an exception message and a throwable cause of the InvalidParameterException class
     * should create an instance properly
     *
     */
    @Test
    public void should_create_exception_object_with_msg_and_cause_when_the_msg_and_cause_accepting_constructor_is_called() {
        String exceptionMsg = "My exception message";
        Throwable mockCause = new Throwable();
        InvalidParameterException invalidParameterException = new InvalidParameterException(exceptionMsg, mockCause);
        assertNotNull(invalidParameterException);
        assertEquals(exceptionMsg, invalidParameterException.getMessage());
        assertEquals(mockCause, invalidParameterException.getCause());
    }

    /**
     * Constructor that accepts an exception message, a throwable cause and the flags of the InvalidParameterException class
     * should create an instance properly
     *
     */
    @Test
    public void should_create_exception_object_with_msg_and_cause_and_flags_when_the_msg_and_cause_and_flags_accepting_constructor_is_called() {
        String exceptionMsg = "My exception message";
        Throwable mockCause = new Throwable();
        InvalidParameterException invalidParameterException = new InvalidParameterException(exceptionMsg, mockCause, true, false);
        assertNotNull(invalidParameterException);
        assertEquals(exceptionMsg, invalidParameterException.getMessage());
        assertEquals(mockCause, invalidParameterException.getCause());
    }
}

