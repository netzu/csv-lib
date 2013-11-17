package com.tomtom.csv.read.dictionary;

import com.tomtom.csv.read.exception.DictionaryReaderException;
import org.junit.Test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
/**
 * Tests for Dictionary csv reader class.
 */
public class DictionaryCsvReaderTest {

    private static final String SEPARATOR = "|";
    public static final String ID_COLUMN = "id";
    public static final String NAME_COLUMN = "name";
    public static final String AGE_COLUMN = "age";
    public static final String EMAIL_COLUMN = "email";

    @Test
    public void readFileOnlyWithHeader() {

        final InputStream is = getStreamToResource("dictionary/file_with_header_only.csv");

        final DictionaryCsvReaderFactory factory = new DictionaryCsvReaderFactory();

        final DictionaryCsvReader dictReader = factory.getCsvReader(is, SEPARATOR);

        List<Map<String, String>> csvContent = readAllData(dictReader);

        assertNotNull(csvContent);
        assertTrue(csvContent.isEmpty());

    }

    @Test
    public void readFileWithAllFieldNotEmpty() {
        InputStream is = getStreamToResource("dictionary/file_with_header.csv");

        DictionaryCsvReader csvReader = new DictionaryCsvReaderFactory().getCsvReader(is, SEPARATOR);

        final List<Map<String, String>> result = readAllData(csvReader);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());

        Map<String, String> csvEntry = result.get(0);

        assertTrue(csvEntry.containsKey(ID_COLUMN));
        assertEquals("5", csvEntry.get(ID_COLUMN));

        assertTrue(csvEntry.containsKey(NAME_COLUMN));
        assertEquals("Tomas", csvEntry.get(NAME_COLUMN));

        assertTrue(csvEntry.containsKey(AGE_COLUMN));
        assertEquals("65", csvEntry.get(AGE_COLUMN));

        assertTrue(csvEntry.containsKey(EMAIL_COLUMN));
        assertEquals("t.brudnicki@gmail.com", csvEntry.get(EMAIL_COLUMN));

    }

    @Test
    public void amountOfHeaderElementsIsDifferentThanInEntry() {
        final InputStream is = getStreamToResource("dictionary/header_has_more_elements.csv");

        DictionaryCsvReader csvReader = new DictionaryCsvReaderFactory().getCsvReader(is, SEPARATOR);

        try {

            readAllData(csvReader);
            fail("Previous call should throw an exception due different size of header than csv entry");
        } catch (final DictionaryReaderException dre) {

        }
    }


    @Test
    public void csvHasDuplicatedColumn() {

        final InputStream is = getStreamToResource("dictionary/duplicated_column.csv");
        DictionaryCsvReader csvReader = new DictionaryCsvReaderFactory().getCsvReader(is, SEPARATOR);

        List<Map<String, String>> result = readAllData(csvReader);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());

        Map<String, String> firstElement = result.get(0);

        assertTrue(firstElement.containsKey(NAME_COLUMN));
        assertEquals("Ryszard", firstElement.get(NAME_COLUMN));

    }


    private List<Map<String, String>> readAllData(final DictionaryCsvReader dictionaryCsvReader) {
        List<Map<String, String>> result = new ArrayList<Map<String, String>>();

        final Iterator<Map<String, String>> it = dictionaryCsvReader.iterator();

        while (it.hasNext()) {
            result.add(it.next());
        }

        return result;
    }

    private InputStream getStreamToResource(final String resourceName) {

        return this.getClass().getClassLoader().getResourceAsStream(resourceName);

    }

}
