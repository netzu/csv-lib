package com.tomtom.csv.write;

import com.tomtom.csv.write.exception.CsvWriteException;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Basic csv writer test class.
 */
public class BasicCsvWriterTest {

    private static final String SEPARATOR = "|";

    private BasicCsvWriter basicCsvWriter;
    private OutputStream mockOutputStream;
    private BufferedWriter mockBufferedWriter;

    @Before
    public void setup() {
        mockOutputStream = mock(OutputStream.class);
        mockBufferedWriter = mock(BufferedWriter.class);
        basicCsvWriter = new BasicCsvWriter(mockOutputStream, SEPARATOR) {

            BufferedWriter prepareBufferedWriter(final OutputStream os) {

                assertEquals(mockOutputStream, os);
                return mockBufferedWriter;
            }
        };
    }


    @Test
    public void writeSingleElement() throws IOException {

        List<String> elements = Arrays.asList("some", "elements", "in", "here");
        basicCsvWriter.write(elements);

        String expectedSavedLine = "some|elements|in|here";

        verify(mockBufferedWriter).write(expectedSavedLine);

    }

    @Test
    public void writeOperationCauseAnException() throws IOException {
        List<String> elements = Arrays.asList("some", "elements", "in", "here");

        doThrow(new IOException("Error occur")).when(mockBufferedWriter).write(anyString());

        try {
            this.basicCsvWriter.write(elements);
            fail("Previous call should throw an exception");
        } catch (final CsvWriteException cwe) {

        }
    }

}
