package csv.read.bean.exception;

/**
 * Exception class for Bean analyze errors.
 */
public class BeanInspectionException extends RuntimeException {

    public BeanInspectionException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public BeanInspectionException(final String message) {
        super(message);
    }

}
