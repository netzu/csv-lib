package com.tomtom.csv.write.bean;

import com.tomtom.csv.write.dictionary.DictionaryCsvWriter;

/**
 * Class for writing annotated classes into csv file.
 */
public class BeanCsvWriter<T> {

    private final DictionaryCsvWriter dictWriter;


    public BeanCsvWriter(final DictionaryCsvWriter dictionaryCsvWriter) {
        this.dictWriter = dictionaryCsvWriter;
    }

    public void write(final T bean) {

    }

}
