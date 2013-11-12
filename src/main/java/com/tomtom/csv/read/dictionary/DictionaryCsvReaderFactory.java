package com.tomtom.csv.read.dictionary;

import com.tomtom.csv.read.BasicCsvReader;
import com.tomtom.csv.read.exception.HeaderReadException;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

/**
 * Factorial class for DictionaryCsvReader.
 */
public class DictionaryCsvReaderFactory {

    public DictionaryCsvReaderFactory() {

    }


    public DictionaryCsvReader getCsvReader(final InputStream is, final String separator) {

        final BasicCsvReader reader = getBasicCsvReaderInstance(is, separator);

        Iterator<List<String>> it = reader.iterator();

        if (it.hasNext()) {
            final List<String> header = it.next();

            if (containsEmptyColumns(header)) {
                throw new HeaderReadException("Header contains empty column in header");
            }

            return getDictionaryCsvReaderInstance(it, header);
        } else {
            throw new HeaderReadException("Was not able to read the header, stream was end");
        }

    }

    private boolean containsEmptyColumns(final List<String> header) {
        return header.contains("");
    }

    BasicCsvReader getBasicCsvReaderInstance(final InputStream is, final String separator) {
        return new BasicCsvReader(is, separator);
    }

    DictionaryCsvReader getDictionaryCsvReaderInstance(final Iterator<List<String>> rowIterator, final List<String> header) {
        return new DictionaryCsvReader(rowIterator, header);
    }

}
