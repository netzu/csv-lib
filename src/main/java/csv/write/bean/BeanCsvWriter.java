package csv.write.bean;

import csv.ObjectInspector;
import csv.write.dictionary.DictionaryCsvWriter;
import csv.write.exception.CsvWriteException;

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
    private final String nullLiteral;
    private boolean lazyDictionaryWriterInitialization;

    /**
     * Constructor. When this is used, the order of storing items will be related to the order of retrieved annotated
     * fields in first stored bean. Direct construction of BeanCsvWriter is not recommended, it should be created, by
     * dedicated class for this purpose - BeanCsvWriterBuilder.
     *
     * @param dictionaryCsvWriter instance of dictionary writer.
     * @param nullLiteral         literal for handling null references.
     * @param lazyDictionaryWriterInitialization indicates that bean writer should extract header definition, according to first written object.
     *                                           It mean that header in DictionaryWriter is set
     */
    BeanCsvWriter(final DictionaryCsvWriter dictionaryCsvWriter, final String nullLiteral, final boolean lazyDictionaryWriterInitialization) {
        this.dictWriter = dictionaryCsvWriter;
        this.nullLiteral = nullLiteral;
        this.lazyDictionaryWriterInitialization = lazyDictionaryWriterInitialization;
    }

    public void write(final T bean) {
        try {
            Map<String, Method> getters = ObjectInspector.searchAnnotatedFieldsGetters(bean);

            if (lazyDictionaryWriterInitialization) {
                List<String> extractedHeaderDefinition = new ArrayList<String>(getters.keySet());
                this.dictWriter.setHeader(extractedHeaderDefinition);
                lazyDictionaryWriterInitialization = false;
            }

            Map<String, String> csvEntry = new HashMap<String, String>();

            for (final String fieldName : getters.keySet()) {

                Method getter = getters.get(fieldName);
                Object invoke = getter.invoke(bean);

                if (null == invoke) {
                    invoke = this.nullLiteral;
                }

                csvEntry.put(fieldName, invoke.toString());

            }

            if (!csvEntry.isEmpty()) {
                this.dictWriter.write(csvEntry);
            }

        } catch (final IllegalAccessException iae) {
            throw new CsvWriteException("Error occur when trying to write bean", iae);
        } catch (final InvocationTargetException ite) {
            throw new CsvWriteException("Error occur when trying to write bean", ite);
        }


    }

}
