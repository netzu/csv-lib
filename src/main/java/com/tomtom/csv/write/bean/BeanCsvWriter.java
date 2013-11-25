package com.tomtom.csv.write.bean;

import com.tomtom.csv.ObjectInspector;
import com.tomtom.csv.write.dictionary.DictionaryCsvWriter;
import com.tomtom.csv.write.exception.CsvWriteException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Class for writing annotated classes into csv file.
 */
public class BeanCsvWriter<T> {

    private final DictionaryCsvWriter dictWriter;
    private final List<String> header;

    /**
     * Constructor. When this is used, the order of storing items will be related to the order of retrieved annotated
     * fields in first stored bean. Direct construction of BeanCsvWriter is not recommended, it should be created, by
     * dedicated class for this purpose, to separate user from the internals.
     *
     * @param dictionaryCsvWriter instance of dictionary writer.
     */
    BeanCsvWriter(final DictionaryCsvWriter dictionaryCsvWriter) {
        this.dictWriter = dictionaryCsvWriter;
        header = new ArrayList<String>();
    }

    /**
     * Constructor. This construction should be used when user is interested in string items in the stream in the given order.
     * Direct construction of BeanCsvWriter is not recommended, it should be created, by dedicated class for this purpose, to separate user from the internals.
     *
     * @param dictionaryCsvWriter instance od dictionary writer.
     * @param header              definition od header that will be decide about the order of the item stored to the stream.
     */
    BeanCsvWriter(final DictionaryCsvWriter dictionaryCsvWriter, final List<String> header) {
        this(dictionaryCsvWriter);
        this.header.addAll(header);
    }

    public void write(final T bean) {
        try {
            Map<String, Method> getters = ObjectInspector.searchAnnotatedFieldsGetters(bean);

            if (!isHeaderInitialized()) {
                this.header.addAll(getters.keySet());
            }
            //LinedHashMap is used to keep the order.
            Map<String, String> csvEntry = new LinkedHashMap<String, String>();

            for (final String fieldName : this.header) {

                Method getter = getters.get(fieldName);
                Object invoke = getter.invoke(bean);

                csvEntry.put(fieldName, invoke.toString());
            }

            this.dictWriter.write(csvEntry);
        } catch (final IllegalAccessException iae) {
            throw new CsvWriteException("Error occur when trying to write bean", iae);
        } catch (final InvocationTargetException ite) {
            throw new CsvWriteException("Error occur when trying to write bean", ite);
        }


    }

    private boolean isHeaderInitialized() {
        return !this.header.isEmpty();
    }

}
