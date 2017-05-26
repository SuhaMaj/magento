package asgard.prometheus.email.client.exceptions;

/**
 * Exception representing an invalid parameter.
 *
 */
public class InvalidParameterException extends Exception {
    /**
     * Creates an instance of the InvalidParameterException.
     *
     */
    public InvalidParameterException() {
        super();
    }

    /**
     * Creates an instance of the InvalidParameterException.
     *
     * @param message   The exception message.
     */
    public InvalidParameterException(String message) {
        super(message);
    }

    /**
     * Creates an instance of the InvalidParameterException.
     *
     * @param cause The cause.
     */
    public InvalidParameterException(Throwable cause) {
        super(cause);
    }

    /**
     * Creates an instance of the InvalidParameterException.
     *
     * @param message   The exception message.
     * @param cause     The cause.
     */
    public InvalidParameterException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates an instance of the InvalidParameterException.
     *
     * @param message               The exception message.
     * @param cause                 The cause.
     * @param enableSuppression     Enable suppression.
     * @param writableStackTrace    Writable stack trace.
     */
    public InvalidParameterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
