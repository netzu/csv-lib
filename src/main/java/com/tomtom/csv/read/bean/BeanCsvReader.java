package com.tomtom.csv.read.bean;

import com.tomtom.csv.read.bean.exception.BeanCsvReaderException;
import com.tomtom.csv.read.bean.exception.BeanInspectionException;
import com.tomtom.csv.read.bean.translation.StringTranslation;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Class for reading annotated java beans from csv.
 */
public class BeanCsvReader<T> implements Iterable<T> {

    private final Iterator<Map<String, String>> dictionaryIterator;
    private final Class<T> clazz;

    private Map<Class<?>, StringTranslation> translationRegistry;


    BeanCsvReader(final Iterator<Map<String, String>> dictionaryIterator, final Class<T> clazz) {
        this.dictionaryIterator = dictionaryIterator;
        this.clazz = clazz;
        translationRegistry = new HashMap<Class<?>, StringTranslation>();
    }


    @Override
    public Iterator<T> iterator() {
        return new BeanIterator();
    }

    public <E> void  registerTranslator(final Class<E> clazz, final StringTranslation<E> translation) {
        this.translationRegistry.put(clazz, translation);
    }

    private class BeanIterator implements Iterator<T> {

        @Override
        public boolean hasNext() {
            return dictionaryIterator.hasNext();
        }

        @Override
        public T next() {

            try {
                final Map<String, String> row = dictionaryIterator.next();

                Constructor<T> constructor = clazz.getConstructor();
                final Object object = constructor.newInstance();

                for (final String csvField : row.keySet()) {
                    Map<String, Method> setters = SetterInspector.getSetters(object);

                    Method setterMethod = setters.get(csvField);

                    if (null == setterMethod) {
                        continue;
                    }

                    Class<?>[] parameterTypes = setterMethod.getParameterTypes();
                    if (parameterTypes.length != 1) {
                        throw new BeanInspectionException(String.format("Expected setter method with 1 parameter but got %s method name %s", parameterTypes.length, setterMethod.getName()));
                    }
                    final Class<?> setterParameterType = parameterTypes[0];

                    final StringTranslation translation = translationRegistry.get(setterParameterType);

                    if (null == translation) {
                        throw new BeanCsvReaderException("No translation logic registered for type " + setterParameterType.getName());
                    }


                    setterMethod.invoke(object, translation.translate(row.get(csvField)));

                }

                return (T) object;

            } catch (final NoSuchMethodException e) {
                throw new BeanInspectionException(String.format("Empty constructor for class %s was not found", clazz.getName()));
            } catch (final InvocationTargetException e) {
                throw new BeanInspectionException("Error occur when constructing an object", e);
            } catch (final InstantiationException e) {
                throw new BeanInspectionException("Error occur when constructing an object", e);
            } catch (final IllegalAccessException e) {
                throw new BeanInspectionException("Error occur when constructing an object", e);
            }


        }

        /**
         * Throws UnsupportedOperationException when call this method.
         */
        @Override
        public void remove() {
            throw new UnsupportedOperationException("Removal operation is not supported");
        }
    }
}
