package com.tomtom.csv.read.bean.translation;

/**
 * String to double translation class.
 */
public class StringToDoubleTranslation implements StringTranslation<Double> {
    @Override
    public Double translate(final String stringRepresentation) {
        return Double.valueOf(stringRepresentation);
    }


    @Override
    public Double getNullRepresentation() {
        return null;
    }
}
