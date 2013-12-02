package csv.read.bean;

/**
 * Exception class for indicating Pojo csv reader problems.
 */
public class BeanReaderException extends RuntimeException {

    public BeanReaderException(final String message) {
        super(message);
    }

    public BeanReaderException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
