package csv.read.bean.translation;

/**
 * String to integer translation implementation.
 */
public class StringToIntegerTranslation implements StringTranslation<Integer> {
    @Override
    public Integer translate(final String stringRepresentation) {
        return Integer.valueOf(stringRepresentation);
    }


    @Override
    public Integer getNullRepresentation() {
        return null;
    }
}
