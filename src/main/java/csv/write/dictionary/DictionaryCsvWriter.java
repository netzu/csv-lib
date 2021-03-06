package csv.write.dictionary;

import csv.write.BasicCsvWriter;
import csv.write.exception.CsvWriteException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Csv writer for dictionary.
 */
public class DictionaryCsvWriter {

    private final BasicCsvWriter basicWriter;
    private List<String> header;

    /**
     * Protected constructor, prefered way to instantiation the DictionaryCsvWriter, by end user is with
     * usage of DictionaryCsvWriterFactory.
     *
     * @param basicWriter instance of basic writer.
     * @param header list of string represents the header.
     *
     */
    DictionaryCsvWriter(final BasicCsvWriter basicWriter, final List<String> header) {

        this.basicWriter = basicWriter;
        this.header = header;
    }



    public void write(final Map<String, String> csvEntry) {

        if (csvEntry.size() < this.header.size()) {
            throw new CsvWriteException("Given csv entry has less elements than expected by size of the header");
        }

        List<String> elements = new ArrayList<String>();

        for (final String columnName: this.header) {

            if (!csvEntry.containsKey(columnName)) {
                throw new CsvWriteException(String.format("Expected column %s was not given int csv entry", columnName));
            }
            String entry = csvEntry.get(columnName);


            elements.add(entry);


        }

        basicWriter.write(elements);


    }

    public void setHeader(final List<String> header) {
        this.header = header;
    }
}
