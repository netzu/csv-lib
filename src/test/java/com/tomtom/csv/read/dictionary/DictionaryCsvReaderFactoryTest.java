package com.tomtom.csv.read.dictionary;


import com.tomtom.csv.read.BasicCsvReader;
import com.tomtom.csv.read.exception.HeaderReadException;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

public class DictionaryCsvReaderFactoryTest {

    private static final List<String> EXAMPLE_HEADER = Arrays.asList("some", "header", "it", "is");
    private static final String SEPARATOR = "|";
    private InputStream mockInputStream;
    private DictionaryCsvReaderFactory factory;
    private BasicCsvReader mockBasicCsvReader;
    private Iterator<List<String>> mockIterator;
    private DictionaryCsvReader mockDictionaryReader;

    @Before
    public void setup() {
        mockInputStream = mock(InputStream.class);
        mockBasicCsvReader = mock(BasicCsvReader.class);
        mockIterator = mock(Iterator.class);
        mockDictionaryReader = mock(DictionaryCsvReader.class);
        factory = new DictionaryCsvReaderFactory() {


            BasicCsvReader getBasicCsvReaderInstance(final InputStream is, final String separator) {

                assertEquals(mockInputStream, is);
                assertEquals(SEPARATOR, separator);

                return mockBasicCsvReader;
            }

            @Override
            DictionaryCsvReader getDictionaryCsvReaderInstance(final Iterator<List<String>> iterator, final List<String> header) {
                assertEquals(mockIterator, iterator);
                assertEquals(EXAMPLE_HEADER, header);

                return mockDictionaryReader;
            }
        };

        when(mockBasicCsvReader.iterator()).thenReturn(mockIterator);
        when(mockIterator.hasNext()).thenReturn(true);
        when(mockIterator.next()).thenReturn(EXAMPLE_HEADER);
    }

    @Test
    public void creationOfDictionaryReaderFileNotEmpty() {

        DictionaryCsvReader csvReader = factory.getCsvReader(mockInputStream, SEPARATOR);

        assertNotNull(csvReader);

        verify(mockBasicCsvReader).iterator();
        verify(mockIterator).hasNext();
        verify(mockIterator).next();
        assertEquals(mockDictionaryReader, csvReader);
    }

    @Test
    public void createDictionaryReaderForEmptyStream() {

        when(mockIterator.hasNext()).thenReturn(false);

        try {
            factory.getCsvReader(mockInputStream, SEPARATOR);
            fail("Previous call should cause an exception due creation of reader from empty stream");
        } catch (final HeaderReadException readException) {

        }

    }

    @Test
    public void csvHeaderContainsEmptyColumn() {

        InputStream is = this.getClass().getClassLoader().getResourceAsStream("dictionary/header_contains_empty_column.csv");

        final DictionaryCsvReaderFactory localFactory = new DictionaryCsvReaderFactory();

        try {
            localFactory.getCsvReader(is, SEPARATOR);
            fail("Previous call should throw an exception due empty column in csv header");
        } catch (final HeaderReadException ex) {

        }

    }


}
