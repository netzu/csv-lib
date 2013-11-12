package com.tomtom.csv.read;

import org.junit.Test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static junit.framework.Assert.*;

/**
 * Test class for BasicCsvReadTest class.
 */
public class BasicCsvReadTest {

    private static final String TEST_SEPARATOR = "|";

    @Test
    public void readExampleCSVFile() {
        final InputStream is = getStreamToResourceFile("basic/example_csv_file.csv");

        final BasicCsvReader basicCsvReader = new BasicCsvReader(is, TEST_SEPARATOR);

        final List<List<String>> result = readAllData(basicCsvReader);

        assertFalse(result.isEmpty());
        assertEquals(2, result.size());

        final List<String> firstEntry = result.get(0);

        assertFalse(firstEntry.isEmpty());
        assertEquals(6, firstEntry.size());

        assertEquals("some", firstEntry.get(0));
        assertEquals("data", firstEntry.get(1));
        assertEquals("is", firstEntry.get(2));
        assertEquals("here", firstEntry.get(3));
        assertEquals("", firstEntry.get(4));
        assertEquals("", firstEntry.get(5));

        final List<String> secondItem = result.get(1);

        assertFalse(secondItem.isEmpty());
        assertEquals(3, secondItem.size());

        assertEquals("1", secondItem.get(0));
        assertEquals("3", secondItem.get(1));
        assertEquals("5454", secondItem.get(2));
    }

    @Test
    public void readEmptyFile() {

        final InputStream is = getStreamToResourceFile("basic/empty_file.csv");

        final BasicCsvReader reader = new BasicCsvReader(is, TEST_SEPARATOR);

        List<List<String>> result = readAllData(reader);

        assertTrue(result.isEmpty());

    }

    @Test
    public void fileEndsWithEmptyLine() {
        final InputStream is = getStreamToResourceFile("basic/ends_with_empty_line.csv");

        final BasicCsvReader reader = new BasicCsvReader(is, TEST_SEPARATOR);
        final List<List<String>> result = readAllData(reader);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());

        final List<String> firstEntry = result.get(0);

        assertFalse(firstEntry.isEmpty());
        assertEquals(3, firstEntry.size());

        assertEquals("1", firstEntry.get(0));
        assertEquals("3", firstEntry.get(1));
        assertEquals("5454", firstEntry.get(2));


    }

    @Test
    public void differentSeparatorTest() {
        final InputStream is = getStreamToResourceFile("basic/different_separator.csv");

        final BasicCsvReader reader = new BasicCsvReader(is, ";");

        final List<List<String>> result = readAllData(reader);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());

        final List<String> firstEntry = result.get(0);

        assertFalse(firstEntry.isEmpty());
        assertEquals(3, firstEntry.size());

        assertEquals("1", firstEntry.get(0));
        assertEquals("3", firstEntry.get(1));
        assertEquals("5454", firstEntry.get(2));

    }

    @Test
    public void removeOperation() {

        final InputStream is = getStreamToResourceFile("basic/different_separator.csv");

        final BasicCsvReader reader = new BasicCsvReader(is, ";");

        final Iterator<List<String>> it = reader.iterator();
        try {

            while (it.hasNext()) {
                List<String> next = it.next();
                it.remove();
                fail("Previous call should throw unsupported operation exception");
            }
        } catch (final UnsupportedOperationException ex) {

        }
    }


    private InputStream getStreamToResourceFile(final String resource) {
        return this.getClass().getClassLoader().getResourceAsStream(resource);
    }

    private static List<List<String>> readAllData(final BasicCsvReader basicCsvReader) {
        final List<List<String>> result = new ArrayList<List<String>>();

        final Iterator<List<String>> it = basicCsvReader.iterator();

        while (it.hasNext()) {
            result.add(it.next());
        }

        return result;
    }
}
