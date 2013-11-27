package com.tomtom.csv.write.dictionary;

import com.tomtom.csv.write.BasicCsvWriter;

import java.io.OutputStream;
import java.util.List;

/**
 * Class responsible for creating DictionaryCsvWriter.
 */
public class DictionaryCsvWriterFactory {


    private final OutputStream os;
    private final String separator;

    /**
     * Constructor.
     *
     * @param os        out stream where data will be pushed.
     * @param separator separator that will be used.
     */
    public DictionaryCsvWriterFactory(final OutputStream os, final String separator) {
        this.os = os;
        this.separator = separator;

    }


    /**
     * Creates an instance of DictionaryCsvWriter, that will be pushing data to the stream given in constructor
     * separated by sign, given in constructor, where expected header is passed as argument of this function.
     *
     * @param header      list of string representing the header of the file.
     * @param writeHeader boolean flag that indicates should before writing any data to stream header will be
     *                    written.
     * @return return ready to use instance od DictionaryCsvWriter.
     */
    public DictionaryCsvWriter createCsvWriter(final List<String> header, final boolean writeHeader) {
        final BasicCsvWriter basicWriter = prepareBasicCSVWriter(this.os, this.separator);

        if (writeHeader) {
            basicWriter.write(header);
        }
        return new DictionaryCsvWriter(basicWriter, header);
    }

    BasicCsvWriter prepareBasicCSVWriter(final OutputStream os, final String separator) {
        return new BasicCsvWriter(os, separator);
    }
}
