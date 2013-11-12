package com.tomtom.csv.read.bean.translation;

/**
 * An interface for translation string to object instance.
 * @param <T> type of object to which translation will be performed.
 *
 */
public interface StringTranslation<T> {

    T translate(final String stringRepresentation);
}
