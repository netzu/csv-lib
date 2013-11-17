package com.tomtom.csv.read.bean.translation;

/**
 * String translation for raw int types. Shares functionality of translation with Integer translation, differs in
 * null representation for this type(assignment 'int a = null' is disallowed)
 */
public class StringToRawIntegerTranslation implements StringTranslation<Integer>{


    @Override
    public Integer translate(final String stringRepresentation) {
        return new StringToIntegerTranslation().translate(stringRepresentation);
    }

    @Override
    public Integer getNullRepresentation() {
        return 0;
    }
}
