package com.tomtom.csv.write.bean;

import com.tomtom.csv.ObjectInspector;
import com.tomtom.csv.write.dictionary.DictionaryCsvWriter;
import com.tomtom.csv.write.exception.CsvWriteException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Class for writing annotated classes into csv file.
 */
public class BeanCsvWriter<T> {

    private final DictionaryCsvWriter dictWriter;
    private final String nullLiteral;

    /**
     * Constructor. When this is used, the order of storing items will be related to the order of retrieved annotated
     * fields in first stored bean. Direct construction of BeanCsvWriter is not recommended, it should be created, by
     * dedicated class for this purpose, to separate user from the internals.
     *
     * @param dictionaryCsvWriter instance of dictionary writer.
     * @param nullLiteral literal for handling null references.
     */
    BeanCsvWriter(final DictionaryCsvWriter dictionaryCsvWriter, final String nullLiteral) {
        this.dictWriter = dictionaryCsvWriter;
        this.nullLiteral = nullLiteral;
    }

    public void write(final T bean) {
        try {
            Map<String, Method> getters = ObjectInspector.searchAnnotatedFieldsGetters(bean);

            Map<String, String> csvEntry = new HashMap<String, String>();

            for (final String fieldName : getters.keySet()) {

                Method getter = getters.get(fieldName);
                Object invoke = getter.invoke(bean);

                if (null == invoke) {
                    invoke = this.nullLiteral;
                }

                csvEntry.put(fieldName, invoke.toString());

            }

            this.dictWriter.write(csvEntry);
        } catch (final IllegalAccessException iae) {
            throw new CsvWriteException("Error occur when trying to write bean", iae);
        } catch (final InvocationTargetException ite) {
            throw new CsvWriteException("Error occur when trying to write bean", ite);
        }


    }

}
