package com.tomtom.csv.read;

import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Class for basic read operations. To iterate through the csv entries you should
 * construct an iterator an iterate over the result.
 */
public class BasicCsvReader implements Iterable<List<String>> {

    private static final int PRESERVING_LIMIT = 10;
    private static final String EMPTY_STRING = "";

    /**
     * Input stream for data.
     */
    private final InputStream is;

    /**
     * CSV elements separator.
     */
    private final String separator;

    public BasicCsvReader(final InputStream is, final String separator) {
        this.is = is;
        this.separator = separator;

    }


    @Override
    public Iterator<List<String>> iterator() {
        return new CSVRowIterator();  //To change body of implemented methods use File | Settings | File Templates.
    }


    /**
     * Iterator for retrieving data from the stream.
     */
    class CSVRowIterator implements Iterator<List<String>> {

        private final BufferedReader buffRead;

        private CSVRowIterator() {
            buffRead = new BufferedReader(new InputStreamReader(is));
        }

        /**
         * Behaves according to Iterator specification.
         *
         * @return true when there is some data in the stream.
         */
        @Override
        public boolean hasNext() {

            try {
                buffRead.mark(PRESERVING_LIMIT);
                int character = buffRead.read();

                if (character != -1) {
                    buffRead.reset();
                    return true;
                } else {
                    return false;
                }


            } catch (final IOException ioe) {
                throw new IllegalStateException("Error occur when inspecting the stream to determine hasNext", ioe);
            }

        }

        /**
         * Method pull from stream data till sign of new line is found. This data is splitted on
         * separator specified by the user in constructor. The result of split is returned as list of string tokens.
         *
         * @return list of splitted tokens
         */
        @Override
        public List<String> next() {

            try {
                final String line = buffRead.readLine();

                return extractElementsFromLine(line);

            } catch (final IOException ioe) {
                throw new IllegalStateException("Error occur when reading the data from stream", ioe);
            }

        }

        private List<String> extractElementsFromLine(final String line) {
            final List<String> result = new LinkedList<String>();

            int startIndex = 0;
            int endIndex = 0;

            while (endIndex != -1) {
                endIndex = StringUtils.indexOf(line, separator, startIndex);

                if (endIndex != -1) {
                    String token = line.substring(startIndex, endIndex);
                    startIndex = endIndex + 1;
                    result.add(token);
                }
            }
            // Line ended with separator it means that at the end there is an empty value.
            if (startIndex == line.length()) {

                result.add(EMPTY_STRING);
            } else {
                //Adding the last item
                result.add(line.substring(startIndex, line.length()));
            }
            return result;

        }

        /**
         * This operation is not allowed. Unsupported operations exception is thrown.
         */
        @Override
        public void remove() {
            throw new UnsupportedOperationException("Operation of removal is not supported");
        }
    }
}
