package com.tomtom.csv.read.bean.translation;

/**
 * Translation class for double type.
 */
public class StringToRawDoubleTranslation implements StringTranslation<Double>{
    @Override
    public Double translate(final String stringRepresentation) {

        return new StringToDoubleTranslation().translate(stringRepresentation);

    }

    @Override
    public Double getNullRepresentation() {
        return 0.0d;
    }
}
