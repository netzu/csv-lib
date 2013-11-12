package com.tomtom.csv.write.dictionary;

import com.tomtom.csv.write.BasicCsvWriter;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Csv writer for dictionary.
 */
public class DictionaryCsvWriter {

    private final BasicCsvWriter basicWriter;
    private final List<String> header;


    public DictionaryCsvWriter(final BasicCsvWriter basicWriter, final List<String> header) {

        this.basicWriter = basicWriter;
        this.header = header;
    }


    public void write(final Map<String, String> csvEntry) {

        List<String> elements = new ArrayList<String>();

        for (final String columnName: this.header) {

            elements.add(csvEntry.get(columnName));

        }

        basicWriter.write(elements);


    }
}
