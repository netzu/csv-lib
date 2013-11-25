package com.tomtom.csv.write.bean;

import com.tomtom.csv.write.dictionary.DictionaryCsvWriter;
import com.tomtom.csv.write.dictionary.DictionaryCsvWriterFactory;

import java.io.OutputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Builder for BeanCsvWriter.
 */
public class BeanCsvWriterBuilder {

    private static final String DEFAULT_NULL_HANDLER = "null";
    private static final boolean DEFAULT_STORE_HEADER = false;
    private static final String DEFAULT_SEPARATOR = "|";


    private final OutputStream os;
    private String nullLiteral = DEFAULT_NULL_HANDLER;
    private boolean storeHeader = DEFAULT_STORE_HEADER;
    private String separator = DEFAULT_SEPARATOR;
    private List<String> header = Collections.<String>emptyList();

    /**
     * Constructor.
     * @param os initialized stream where data will be stored.
     */
    public BeanCsvWriterBuilder(final OutputStream os) {
        this.os = os;
    }

    /**
     * Should first store the header.
     * @param writeHeader store header.
     * @return builder instance.
     */
    public BeanCsvWriterBuilder writeHeader(final boolean writeHeader) {
        this.storeHeader = writeHeader;

        return this;
    }

    /**
     * Setup null literal.
     * @param nullLiteral null literal that will be used for null bean properties.
     * @return same builder instance.
     *
     */
    public BeanCsvWriterBuilder withNullLiteral(final String nullLiteral) {
        this.nullLiteral = nullLiteral;
        return this;
    }


    /**
     * Setup header definition.
     * @param header header definition as list os strings.
     * @return same builder instance.
     */
    public BeanCsvWriterBuilder withHeader(final List<String> header) {
        this.header = header;
        return this;
    }

    /**
     * Builds the bean writer instance.
     * @param separator separator that will be used.
     * @param <T> type of bean that will be stored.
     *
     * @return ready to use instance of BeanCsvWriter.
     */
    public <T> BeanCsvWriter<T> build(final String separator) {
        DictionaryCsvWriterFactory dictionaryFactory = new DictionaryCsvWriterFactory(this.os, separator);

        boolean storeHeader = this.storeHeader && !this.header.isEmpty();

        DictionaryCsvWriter csvWriter = dictionaryFactory.createCsvWriter(this.header, storeHeader);

        BeanCsvWriter<T> beanCsvWriter = new BeanCsvWriter<T>(csvWriter, this.nullLiteral, !storeHeader);

        return beanCsvWriter;
    }

}
