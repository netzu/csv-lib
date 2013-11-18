package com.tomtom.csv.read.bean;

import com.tomtom.csv.read.bean.exception.BeanInspectionException;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Class for lookup in objects for setters methods for annotated wit CSVProperty annotation fields in class.
 */
final class SetterInspector {

    /**
     * Private constructor to prevent instantiation.
     */
    private SetterInspector() {

    }

    /**
     * This method inspects the given object and lookup for fields annotated by CSVProperty annotation.
     * Those which are annotated will be returned as map, where key is the name stored in annotation in given object,
     * value is the setter Method object for this property.
     * @param input object for inspection.
     * @return map of csv field name mapped on setter method.
     */
    static Map<String, Method> getSetters(final Object input) {

        final Map<String, Method> result = new HashMap<String, Method>();

        try {
        for (final Field field : input.getClass().getDeclaredFields()) {


            if (isAnnotatedField(field)) {

                final PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), input.getClass());
                Method setter = propertyDescriptor.getWriteMethod();

                if (null == setter) {
                    throw new BeanInspectionException("No setter method found for annotated class field " + field.getName());
                }


                String csvFieldName = getCSVFiledFromAnnotatedField(field);
                result.put(csvFieldName, setter);


            }

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


}
