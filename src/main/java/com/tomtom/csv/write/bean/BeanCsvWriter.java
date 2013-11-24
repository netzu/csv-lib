package com.tomtom.csv.write.bean;

import com.tomtom.csv.ObjectInspector;
import com.tomtom.csv.write.dictionary.DictionaryCsvWriter;
import com.tomtom.csv.write.exception.CsvWriteException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class for writing annotated classes into csv file.
 */
public class BeanCsvWriter<T> {

    private final DictionaryCsvWriter dictWriter;
    private final List<String> header;

    BeanCsvWriter(final DictionaryCsvWriter dictionaryCsvWriter) {
        this.dictWriter = dictionaryCsvWriter;
        header = new ArrayList<String>();
    }

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

            Map<String, String> csvEntry = new HashMap<String, String>();

            for (final String fieldName : this.header) {

                Method getter = getters.get(fieldName);

                if (null == getter) {
                    throw new CsvWriteException(String.format("For given field %s bean property was not found", fieldName));
                }

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
