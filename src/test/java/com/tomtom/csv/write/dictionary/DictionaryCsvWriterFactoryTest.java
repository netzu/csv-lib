package com.tomtom.csv.write.dictionary;

import com.tomtom.csv.write.BasicCsvWriter;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;

import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.isNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Created with IntelliJ IDEA.
 * User: mht
 * Date: 11/27/13
 * Time: 10:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class DictionaryCsvWriterFactoryTest {

    private static final String SEPARATOR = ";";
    private DictionaryCsvWriterFactory dictFactory;
    private OutputStream mockOutputStream;
    private BasicCsvWriter mockBasicWriter;


    @Before
    public void setup() {
        mockOutputStream = mock(OutputStream.class);
        mockBasicWriter = mock(BasicCsvWriter.class);
        dictFactory = new DictionaryCsvWriterFactory(mockOutputStream, SEPARATOR) {

            @Override
            BasicCsvWriter prepareBasicCSVWriter(final OutputStream os, final String separator) {
                assertThat(os, is(equalTo(mockOutputStream)));
                assertThat(separator, is(equalTo(SEPARATOR)));
                return mockBasicWriter;
            }
        };
    }

    @Test
    public void headerIsSaved() {

        List<String> headers = Arrays.asList("one", "three", "four");

        dictFactory.createCsvWriter(headers, true);

        verify(mockBasicWriter).write(headers);
    }

    @Test
    public void headerNotSaved() {

        List<String> headers = Arrays.asList("one", "three", "four");

        dictFactory.createCsvWriter(headers, false);

        verifyNoMoreInteractions(mockBasicWriter);

    }


}
