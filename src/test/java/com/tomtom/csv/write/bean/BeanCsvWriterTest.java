package com.tomtom.csv.write.bean;

import com.tomtom.csv.read.bean.exception.BeanInspectionException;
import com.tomtom.csv.write.bean.samples.BeanWithNoGetter;
import com.tomtom.csv.write.bean.samples.SimpleBean;
import com.tomtom.csv.write.dictionary.DictionaryCsvWriter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatcher;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.Assert.fail;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.*;

/**
 * Test class for BeanCsvWriter.
 */
public class BeanCsvWriterTest {

    private static final String DEFAULT_NULL_LITERAL = "null";

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

        BeanCsvWriter<SimpleBean> beanWriter = new BeanCsvWriter<SimpleBean>(mockDictWriter, DEFAULT_NULL_LITERAL, false);
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

        BeanCsvWriter<BeanWithNoGetter> writer = new BeanCsvWriter<BeanWithNoGetter>(mockDictWriter, DEFAULT_NULL_LITERAL, false);

        try {
            writer.write(bean);
            fail("Previous call should throw an exception due to non existing getter for property");
        } catch (final BeanInspectionException bie) {

        }
    }

    @Test
    public void writeBeanWithNullProperty(){
        final SimpleBean simpleBean = new SimpleBean();

        simpleBean.setId(675L);
        simpleBean.setName(null);
        simpleBean.setAge(null);

        final String newNullLiteral = "Some_LITERAL";

        final BeanCsvWriter<SimpleBean> writer = new BeanCsvWriter<SimpleBean>(mockDictWriter, newNullLiteral, false);
        writer.write(simpleBean);

        final Map<String, String> expectedMap = new HashMap<String, String>();
        expectedMap.put("age", newNullLiteral);
        expectedMap.put("id", "675");
        expectedMap.put("name", newNullLiteral);

        verify(mockDictWriter).write(expectedMap);

    }

    @Test
    public void lazyInitializationIsSetToFalse() {

        final SimpleBean simpleBean = new SimpleBean();

        simpleBean.setId(675L);
        simpleBean.setName(null);
        simpleBean.setAge(null);

        final String newNullLiteral = "Some_LITERAL";

        final BeanCsvWriter<SimpleBean> writer = new BeanCsvWriter<SimpleBean>(mockDictWriter, newNullLiteral, false);
        writer.write(simpleBean);

        final Map<String, String> expectedMap = new HashMap<String, String>();
        expectedMap.put("age", newNullLiteral);
        expectedMap.put("id", "675");
        expectedMap.put("name", newNullLiteral);

        verify(mockDictWriter).write(expectedMap);
        verifyNoMoreInteractions(mockDictWriter);
    }

    @Test
    public void lazyInitializationSetToTrue() {

        final SimpleBean simpleBean = new SimpleBean();

        simpleBean.setId(675L);
        simpleBean.setName(null);
        simpleBean.setAge(null);

        final String newNullLiteral = "Some_LITERAL";

        final BeanCsvWriter<SimpleBean> writer = new BeanCsvWriter<SimpleBean>(mockDictWriter, newNullLiteral, true);
        writer.write(simpleBean);

        final Map<String, String> expectedMap = new HashMap<String, String>();
        expectedMap.put("age", newNullLiteral);
        expectedMap.put("id", "675");
        expectedMap.put("name", newNullLiteral);

        verify(mockDictWriter).write(expectedMap);
        verify(mockDictWriter).setHeader(argThat(new CollectionMather<String>(expectedMap.keySet())));
    }

    @Test
    public void dictionaryCsvWriterInitializationIsMadeOnce() {

        final SimpleBean simpleBean = new SimpleBean();

        simpleBean.setId(675L);
        simpleBean.setName(null);
        simpleBean.setAge(null);

        final String newNullLiteral = "Some_LITERAL";

        final BeanCsvWriter<SimpleBean> writer = new BeanCsvWriter<SimpleBean>(mockDictWriter, newNullLiteral, true);
        writer.write(simpleBean);

        final Map<String, String> expectedMap = new HashMap<String, String>();
        expectedMap.put("age", newNullLiteral);
        expectedMap.put("id", "675");
        expectedMap.put("name", newNullLiteral);
        writer.write(simpleBean);

        verify(mockDictWriter, times(2)).write(expectedMap);
        verify(mockDictWriter).setHeader(argThat(new CollectionMather<String>(expectedMap.keySet())));

        verifyNoMoreInteractions(mockDictWriter);

    }

    private class CollectionMather<E> extends ArgumentMatcher<List<E>> {

        private final Collection<E> expectedCollection;

        private CollectionMather(final Collection<E> collection) {
            expectedCollection = collection;
        }

        @Override
        public boolean matches(final Object o) {

            if (!(o instanceof Collection)) {
                fail("Expected collection but got " + o.getClass().getName());
            }

            final Collection<E> givenCollection = (Collection<E>) o;

            for (final E item : expectedCollection) {
                if (!givenCollection.contains(item)) {
                   return false;
                }
            }


            return true;  //To change body of implemented methods use File | Settings | File Templates.
        }
    }
}
