package com.tomtom.csv.read.bean.exception;

/**
 * Exception for reporting bean reading errors.
 */
public class BeanCsvReaderException extends RuntimeException {

    public BeanCsvReaderException(final String message) {
        super(message);
    }

    public BeanCsvReaderException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
