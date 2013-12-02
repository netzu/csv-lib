package csv.read.exception;

/**
 * Reading header exception.
 */
public class HeaderReadException extends RuntimeException {

    public HeaderReadException(final String message) {

        super(message);
    }

    public HeaderReadException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
