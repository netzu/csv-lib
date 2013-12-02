package csv.read.bean;

import java.lang.annotation.*;

/**
 * Annotation marking elements to be used in read or write csv operations.
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CSVProperty {
    String name();
}
