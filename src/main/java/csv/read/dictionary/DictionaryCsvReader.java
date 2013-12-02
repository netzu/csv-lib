package csv.read.dictionary;

import csv.read.exception.DictionaryReaderException;

import java.util.*;

/**
 * Reader for csv file which treats each entry as dictionary, where key will be the value
 * stored in correct place in the header of file.
 */
public class DictionaryCsvReader implements Iterable<Map<String, String>> {

    private final Iterator<List<String>> rowIterator;
    private final List<String> header;

    DictionaryCsvReader(final Iterator<List<String>> rowIterator, final List<String> header) {

        this.rowIterator = rowIterator;
        this.header = header;
    }

    @Override
    public Iterator<Map<String, String>> iterator() {
        return new DictionaryCsvReaderIterator();  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<String> getHeader() {
        return Collections.unmodifiableList(header);
    }


    public class DictionaryCsvReaderIterator implements Iterator<Map<String, String>> {



        @Override
        public boolean hasNext() {
            return rowIterator.hasNext();  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public Map<String, String> next() {

            List<String> row = rowIterator.next();
            Map<String, String> result = new HashMap<String, String>();




            if (header.size() != row.size()) {
                throw new DictionaryReaderException("Header has different amount of elements than entry: " + header + "->" + row);
            }

            for (int index = 0; index < header.size(); index++) {
                result.put(header.get(index), row.get(index));
            }

            return result;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Removal operation is not allowed");
        }
    }

}
