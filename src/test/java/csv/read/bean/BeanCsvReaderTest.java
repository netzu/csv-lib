package csv.read.bean;

import csv.read.bean.exception.BeanCsvReaderException;
import csv.read.bean.exception.BeanInspectionException;
import csv.read.bean.samples.*;
import csv.read.bean.translation.StringTranslation;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import org.junit.Test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static junit.framework.Assert.assertNull;
import static junit.framework.TestCase.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;

/**
 * BeanCsvReader test class.
 */
public class BeanCsvReaderTest {

    private static final String SEPARATOR = "|";

    @Test
    public void simpleReadTest() {

        final InputStream is = getStreamToResource("bean/simple_bean.csv");

        BeanCsvReader<SimpleBeanClass> beanCsvReader = prepareCsvReader(is, SEPARATOR, SimpleBeanClass.class);

        Iterator<SimpleBeanClass> beanClassIterator = beanCsvReader.iterator();

        final List<SimpleBeanClass> result = new ArrayList<SimpleBeanClass>();

        while (beanClassIterator.hasNext()) {
            result.add(beanClassIterator.next());
        }

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());

        final SimpleBeanClass sampleClass = result.get(0);

        assertEquals(Integer.valueOf(44), sampleClass.getIdentifier());
        assertEquals("tomasz.brudnicki@tomtom.com", sampleClass.getMail());
        assertEquals(Long.valueOf(33), sampleClass.getLongAge());

    }

    @Test
    public void readBeanWithRawTypes() {

        final InputStream is = getStreamToResource("bean/simple_bean.csv");

        BeanCsvReader<BeanClassWithRawTypes> beanCsvReader = prepareCsvReader(is, SEPARATOR, BeanClassWithRawTypes.class);

        Iterator<BeanClassWithRawTypes> beanClassIterator = beanCsvReader.iterator();

        final List<BeanClassWithRawTypes> result = new ArrayList<BeanClassWithRawTypes>();

        while (beanClassIterator.hasNext()) {
            result.add(beanClassIterator.next());
        }

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());

        final BeanClassWithRawTypes sampleClass = result.get(0);

        assertEquals(44, sampleClass.getId());
        assertEquals("tomasz.brudnicki@tomtom.com", sampleClass.getMail());
        assertEquals(33L, sampleClass.getAge());

    }

    @Test
    public void csvFileHasMoreFieldsThanClassAnnotatedFields() {
        final InputStream is = getStreamToResource("bean/more_fields.csv");

        BeanCsvReader<SimpleBeanClass> beanCsvReader = prepareCsvReader(is, SEPARATOR, SimpleBeanClass.class);

        Iterator<SimpleBeanClass> beanClassIterator = beanCsvReader.iterator();

        final List<SimpleBeanClass> result = new ArrayList<SimpleBeanClass>();

        while (beanClassIterator.hasNext()) {
            result.add(beanClassIterator.next());
        }

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());

        final SimpleBeanClass sampleClass = result.get(0);

        assertEquals(Integer.valueOf(44), sampleClass.getIdentifier());
        assertEquals("tomasz.brudnicki@tomtom.com", sampleClass.getMail());
        assertEquals(Long.valueOf(33), sampleClass.getLongAge());

    }

    @Test
    public void beanClassHasMoreFieldsThanCsv() {
        final InputStream is = getStreamToResource("bean/simple_bean.csv");

        BeanCsvReader<SimpleBeanClass2> beanCsvReader = prepareCsvReader(is, SEPARATOR, SimpleBeanClass2.class);

        Iterator<SimpleBeanClass2> beanClassIterator = beanCsvReader.iterator();

        final List<SimpleBeanClass2> result = new ArrayList<SimpleBeanClass2>();

        while (beanClassIterator.hasNext()) {
            result.add(beanClassIterator.next());
        }

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());

        final SimpleBeanClass2 sampleClass = result.get(0);

        assertEquals(Integer.valueOf(44), sampleClass.getIdentifier());
        assertEquals("tomasz.brudnicki@tomtom.com", sampleClass.getMail());
        assertEquals(Long.valueOf(33), sampleClass.getLongAge());
        assertNull(sampleClass.getMessage());
    }

    @Test
    public void checkAmountOfReadElements() {
        final InputStream is = getStreamToResource("bean/few_entries.csv");

        BeanCsvReader<SimpleBeanClass> beanCsvReader = prepareCsvReader(is, SEPARATOR, SimpleBeanClass.class);

        Iterator<SimpleBeanClass> beanClassIterator = beanCsvReader.iterator();

        int counter = 0;

        while (beanClassIterator.hasNext()) {

            beanClassIterator.next();

            counter++;
        }

        assertEquals(8, counter);

    }


    @Test
    public void inheritedBeanRead() {

        final InputStream is = getStreamToResource("bean/more_fields.csv");

        BeanCsvReader<ExtendedSimpleBean> beanCsvReader = prepareCsvReader(is, SEPARATOR, ExtendedSimpleBean.class);

        Iterator<ExtendedSimpleBean> beanClassIterator = beanCsvReader.iterator();

        final List<ExtendedSimpleBean> result = new ArrayList<ExtendedSimpleBean>();

        while (beanClassIterator.hasNext()) {
            result.add(beanClassIterator.next());
        }

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());

        final ExtendedSimpleBean extendedSampleClass = result.get(0);

        assertNull(extendedSampleClass.getIdentifier());
        assertNull(extendedSampleClass.getMail());
        assertNull(extendedSampleClass.getLongAge());
        assertEquals("dummy", extendedSampleClass.getMessage());

    }

    @Test
    public void beanFieldTypeHasNotRegisteredTranslation() {

        final InputStream is = getStreamToResource("bean/geometry.csv");

        BeanCsvReader<SimpleBeanWithGeometry> reader = prepareCsvReader(is, SEPARATOR, SimpleBeanWithGeometry.class);

        Iterator<SimpleBeanWithGeometry> it = reader.iterator();

        assertTrue(it.hasNext());

        try {

            it.next();
            fail("Previous call should thrown an exception due to unknown translation for Geometry type, in SimpleBeanWithGeometry");
        } catch (final BeanCsvReaderException bcsre) {
            assertTrue(bcsre.getMessage().startsWith("No translation logic registered for type"));
        }

    }

    @Test
    public void beanWithGeometryWithRegisteredTranslation() {

        final InputStream is = getStreamToResource("bean/geometry.csv");

        BeanCsvReaderBuilder builder = new BeanCsvReaderBuilder();

        builder.withSeparator(SEPARATOR);
        builder.withTranslation(Geometry.class, new GeometryTransaction());

        BeanCsvReader<SimpleBeanWithGeometry> reader = builder.build(is, SimpleBeanWithGeometry.class);

        Iterator<SimpleBeanWithGeometry> it = reader.iterator();

        assertTrue(it.hasNext());
        final SimpleBeanWithGeometry geometryBean = it.next();

        assertEquals(Integer.valueOf(44), geometryBean.getIdentifier());
        assertEquals("tomasz.brudnicki@tomtom.com", geometryBean.getMail());
        assertEquals(Long.valueOf(33), geometryBean.getLongAge());

        final GeometryFactory gf = new GeometryFactory();

        Point expectedGeometry = gf.createPoint(new Coordinate(30, 10));
        Point actualGeometry = (Point) geometryBean.getGeometry();

        assertEquals(expectedGeometry.getX(), actualGeometry.getX(), 0.0000001);
        assertEquals(expectedGeometry.getY(), actualGeometry.getY(), 0.0000001);

        assertFalse(it.hasNext());
    }

    @Test
    public void beanWithNoEmptyConstructor() {
        final InputStream is = getStreamToResource("bean/simple_bean.csv");
        BeanCsvReader<NoEmptyContructorBean> reader = prepareCsvReader(is, SEPARATOR, NoEmptyContructorBean.class);

        Iterator<NoEmptyContructorBean> it = reader.iterator();

        assertTrue(it.hasNext());

        try {
            it.next();
            fail("Previous call should throw an exception due to not existing empty constructor in bean class");
        } catch (BeanInspectionException e) {
            assertTrue(e.getMessage().startsWith("Empty constructor for class"));

        }
    }

    @Test
    public void readNullsForObjectProperties() {

        final InputStream is = getStreamToResource("bean/null_values.csv");
        BeanCsvReader<SimpleBeanClass> reader = prepareCsvReader(is, SEPARATOR, SimpleBeanClass.class);

        Iterator<SimpleBeanClass> it = reader.iterator();


        assertThat(it.hasNext(), is(true));

        SimpleBeanClass next = it.next();
        assertThat(next, notNullValue());
        assertThat(next.getLongAge(), nullValue());
        assertThat(next.getMail(), nullValue());
        assertThat(next.getIdentifier(), is(equalTo(44)));


        assertThat(it.hasNext(), is(false));

    }

    @Test
    public void readNullsForRawTypeProperties() {

        final InputStream is = getStreamToResource("bean/null_values.csv");

        BeanCsvReader<BeanClassWithRawTypes> beanCsvReader = prepareCsvReader(is, SEPARATOR, BeanClassWithRawTypes.class);

        Iterator<BeanClassWithRawTypes> beanClassIterator = beanCsvReader.iterator();

        assertThat(beanClassIterator.hasNext(), is(true));

        final BeanClassWithRawTypes bean = beanClassIterator.next();

        assertThat(bean.getId(), is(equalTo(44)));
        assertThat(bean.getMail(), nullValue());
        assertThat(bean.getAge(), is(equalTo(0L)));

    }

    @Test
    public void readBeanWithNoAnnotatedFields() {
        final InputStream is = getStreamToResource("bean/simple_bean.csv");

        BeanCsvReader<BeanWithNoAnnotatedFields> beanCsvReader = prepareCsvReader(is, SEPARATOR, BeanWithNoAnnotatedFields.class);

        Iterator<BeanWithNoAnnotatedFields> beanClassIterator = beanCsvReader.iterator();

        final List<BeanWithNoAnnotatedFields> result = new ArrayList<BeanWithNoAnnotatedFields>();

        while (beanClassIterator.hasNext()) {
            result.add(beanClassIterator.next());
        }

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());

        final BeanWithNoAnnotatedFields sampleClass = result.get(0);

        assertNull(sampleClass.getIdentifier());
        assertNull(sampleClass.getLongAge());
        assertNull(sampleClass.getMail());
    }



    private InputStream getStreamToResource(final String resourceName) {

        return this.getClass().getClassLoader().getResourceAsStream(resourceName);

    }

    private <T> BeanCsvReader<T> prepareCsvReader(final InputStream is, final String separator, final Class<T> clazz) {
        BeanCsvReaderBuilder builder = new BeanCsvReaderBuilder();

        builder.withSeparator(separator);

        return builder.build(is, clazz);
    }

    private class GeometryTransaction implements StringTranslation<Geometry> {

        @Override
        public Geometry translate(final String stringRepresentation) {

            GeometryFactory gf = new GeometryFactory();
            WKTReader wktReader = new WKTReader(gf);

            try {
                final Geometry result = wktReader.read(stringRepresentation);
                return result;
            } catch (final ParseException pe) {

                throw new BeanReaderException("Error occur when translating " + Geometry.class.getName());
            }
        }


        @Override
        public Geometry getNullRepresentation() {
            return null;
        }
    }

}
