package csv.read.bean.translation;

/**
 * String translation for raw long types. Shares functionality of translation with Long translation, differs in
 * null representation for this type(assignment 'long a = null' is disallowed)
 */
public class StringToRawLongTranslation implements StringTranslation<Long> {
    @Override
    public Long translate(final String stringRepresentation) {
        return new StringToLongTranslation().translate(stringRepresentation);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Long getNullRepresentation() {
        return 0L;
    }
}
