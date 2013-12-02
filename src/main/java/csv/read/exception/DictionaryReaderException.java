package csv.read.exception;

/**
 * An exception class for indicate errors for DictionaryCsvReader functionality.
 */
public class DictionaryReaderException extends RuntimeException {

    public DictionaryReaderException(final String message) {
        super(message);
    }
}
