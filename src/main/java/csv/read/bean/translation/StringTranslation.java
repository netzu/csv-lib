package csv.read.bean.translation;

/**
 * An interface for translation string to object instance.
 * @param <T> type of object to which translation will be performed.
 *
 */
public interface StringTranslation<T> {

    /**
     * Method defines hos to change string representation to instance of object.
     * @param stringRepresentation string that will be dispatched into object.
     * @return instance of expected object.
     */
    T translate(final String stringRepresentation);

    /**
     * Helper method that gives a hint, in case when during reading a null indicator would be found
     * what will should be returned. It can look kind of artificial, but this was introduced to solve problem
     * with raw types, and define behaviour of such translation, externally and give ability of control for user.
     *
     * To understand please have a look for example StringToDoubleTranslation vs StringToRawDoubleTranslation.
     * @return null representation.
     */
    T getNullRepresentation();
}
