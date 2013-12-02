package csv;

import csv.read.bean.CSVProperty;
import csv.read.bean.exception.BeanInspectionException;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class for lookup in objects for setters methods for annotated wit CSVProperty annotation fields in class.
 */
public final class ObjectInspector {

    /**
     * Private constructor to prevent instantiation.
     */
    private ObjectInspector() {

    }

    /**
     * This method inspects the given object and lookup for fields annotated by CSVProperty annotation.
     * Those which are annotated will be returned as map, where key is the name stored in annotation in given object,
     * value is the setter Method object for this property.
     *
     * @param input object for inspection.
     * @return map of csv field name mapped on setter method.
     */
    public static Map<String, Method> searchAnnotatedFieldsSetters(final Object input) {

        final Map<String, Method> result = new HashMap<String, Method>();

        try {
            List<Field> annotatedFields = getAnnotatedFields(input);

            for (final Field annotatedField : annotatedFields) {
                final PropertyDescriptor propertyDescriptor = new PropertyDescriptor(annotatedField.getName(), input.getClass());
                Method setter = propertyDescriptor.getWriteMethod();

                if (null == setter) {
                    throw new BeanInspectionException("No setter method found for annotated class field " + annotatedField.getName());
                }


                String csvFieldName = getCSVFiledFromAnnotatedField(annotatedField);
                result.put(csvFieldName, setter);


            }
        } catch (final NoSuchFieldException nse) {
            throw new BeanInspectionException("Error occur when inspecting : " + input.getClass().getName(), nse);
        } catch (final IntrospectionException ie) {
            throw new BeanInspectionException("Error occur when inspecting : " + input.getClass().getName(), ie);
        }


        return result;
    }

    public static Map<String, Method> searchAnnotatedFieldsGetters(final Object input) {
        final Map<String, Method> result = new HashMap<String, Method>();
        try {
            List<Field> annotatedFields = getAnnotatedFields(input);

            for(final Field annotatedField : annotatedFields) {

                final PropertyDescriptor propertyDescriptor = new PropertyDescriptor(annotatedField.getName(), input.getClass());

                Method getter = propertyDescriptor.getReadMethod();

                if (null == getter) {
                    throw new BeanInspectionException("No getter method found for annotated class field " + annotatedField.getName());
                }

                String csvFieldName = getCSVFiledFromAnnotatedField(annotatedField);
                result.put(csvFieldName, getter);

            }

        } catch (final NoSuchFieldException nse) {
            throw new BeanInspectionException("Error occur when inspecting : " + input.getClass().getName(), nse);
        } catch (final IntrospectionException ie) {
            throw new BeanInspectionException("Error occur when inspecting : " + input.getClass().getName(), ie);
        }

        return result;
    }


    private static boolean isAnnotatedField(final Field field) throws NoSuchFieldException {
        return field.isAnnotationPresent(CSVProperty.class);
    }


    private static String getCSVFiledFromAnnotatedField(final Field annotatedField) {
        return annotatedField.getAnnotation(CSVProperty.class).name();
    }

    private static List<Field> getAnnotatedFields(final Object object) throws NoSuchFieldException {

        final List<Field> result = new ArrayList<Field>();

        for (final Field field : object.getClass().getDeclaredFields()) {
            if (isAnnotatedField(field)) {
                result.add(field);
            }
        }

        return result;
    }


}
