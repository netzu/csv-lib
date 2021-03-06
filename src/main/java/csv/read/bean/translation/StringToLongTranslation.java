package csv.read.bean.translation;

/**
 * String to Long translation implementation.
 */
public class StringToLongTranslation implements StringTranslation<Long> {
    @Override
    public Long translate(final String stringRepresentation) {
        return Long.valueOf(stringRepresentation);
    }

    @Override
    public Long getNullRepresentation() {
        return null;
    }
}
