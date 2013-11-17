package com.tomtom.csv.read.bean.translation;

/**
 * String to string translation implementation.
 */
public class StringToStringTranslation implements StringTranslation<String> {
    @Override
    public String translate(String stringRepresentation) {
        return stringRepresentation;
    }


    @Override
    public String getNullRepresentation() {
        return null;
    }
}
