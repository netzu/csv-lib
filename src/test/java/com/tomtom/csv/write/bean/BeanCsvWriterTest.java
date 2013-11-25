package com.tomtom.csv.write.bean;

import com.tomtom.csv.read.bean.exception.BeanInspectionException;
import com.tomtom.csv.write.bean.samples.BeanWithNoGetter;
import com.tomtom.csv.write.bean.samples.SimpleBean;
import com.tomtom.csv.write.dictionary.DictionaryCsvWriter;
import com.tomtom.csv.write.exception.CsvWriteException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatcher;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Test class for BeanCsvWriter.
 */
public class BeanCsvWriterTest {

    private DictionaryCsvWriter mockDictWriter;


    @Before
    public void setup() {
        mockDictWriter = mock(DictionaryCsvWriter.class);
    }


    @Test
    public void writeSimpleBeanTest() {
        final SimpleBean simpleBean = new SimpleBean();
        simpleBean.setAge(77);
        simpleBean.setId(12L);
        simpleBean.setName("DummyName");

        BeanCsvWriter<SimpleBean> beanWriter = new BeanCsvWriter<SimpleBean>(mockDictWriter);
        beanWriter.write(simpleBean);


        final Map<String, String> expectedMap = new HashMap<String, String>();
        expectedMap.put("age", "77");
        expectedMap.put("id", "12");
        expectedMap.put("name", "DummyName");


        verify(mockDictWriter).write(expectedMap);
    }

    @Test
    public void writeBeanWithoutGetterForAnnotatedProperty() {
        final BeanWithNoGetter bean = new BeanWithNoGetter();
        bean.setName("some_name");
        bean.setId(44L);
        bean.setAge(123);

        BeanCsvWriter<BeanWithNoGetter> writer = new BeanCsvWriter<BeanWithNoGetter>(mockDictWriter);

        try {
            writer.write(bean);
            fail("Previous call should throw an exception due to non existing getter for property");
        } catch (final BeanInspectionException bie) {

        }
    }


    private class MapMatcher extends ArgumentMatcher<Map<String, String>> {

        @Override
        public boolean matches(final Object o) {
            if (!(o instanceof Map)) {
                fail("Given argument is not a type of Map");
            }

            Map<String, String> map = (Map) o;



            return false;  //To change body of implemented methods use File | Settings | File Templates.
        }
    }


}
