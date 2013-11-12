package com.tomtom.csv.write.dictionary;

import com.tomtom.csv.write.BasicCsvWriter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatcher;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created with IntelliJ IDEA.
 * User: mht
 * Date: 11/11/13
 * Time: 10:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class DictionaryCsvWriterTest {


    private BasicCsvWriter mockBasicWriter;
    private DictionaryCsvWriter dictionaryCsvWriter;


    @Before
    public void setup() {
        mockBasicWriter = mock(BasicCsvWriter.class);

    }


    @Test
    public void saveNonEmptyDictionary() {
        Map<String, String> dict = new HashMap<String, String>();

        dict.put("1", "one");
        dict.put("2", "two");
        dict.put("3", "three");

        dictionaryCsvWriter = new DictionaryCsvWriter(mockBasicWriter, Arrays.asList("3", "1", "2"));
        dictionaryCsvWriter.write(dict);

        verify(mockBasicWriter).write(argThat(new ArgumentMatcher<List<String>>() {
            @Override
            public boolean matches(final Object o) {

                List<String> result = (List<String>) o;

                assertThat(result.size(), is(equalTo(3)));
                assertThat(result, is(Arrays.asList("three", "one", "two")));
                return true;  //To change body of implemented methods use File | Settings | File Templates.
            }
        }));

    }






}
