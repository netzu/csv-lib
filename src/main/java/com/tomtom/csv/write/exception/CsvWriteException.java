package com.tomtom.csv.write.exception;

/**
 * Created with IntelliJ IDEA.
 * User: mht
 * Date: 11/11/13
 * Time: 5:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class CsvWriteException extends RuntimeException {

    public CsvWriteException(final String message) {
        super(message);
    }

    public CsvWriteException(final String message, final Throwable cause) {

        super(message, cause);
    }


}
