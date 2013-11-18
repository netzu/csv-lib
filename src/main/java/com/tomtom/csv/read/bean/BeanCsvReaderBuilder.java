package com.tomtom.csv.read.bean;

import com.tomtom.csv.read.bean.translation.*;
import com.tomtom.csv.read.dictionary.DictionaryCsvReader;
import com.tomtom.csv.read.dictionary.DictionaryCsvReaderFactory;

import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Builder class for BeanCsvReaderBuilder.
 */
public class BeanCsvReaderBuilder {

    private String separator;
    private Map<Class<?>, StringTranslation> translationMap;

    public BeanCsvReaderBuilder() {
        this.translationMap = new HashMap<Class<?>, StringTranslation>();
        appendDefaultTranslations(this.translationMap);
    }

    public <T> BeanCsvReaderBuilder withTranslation(final Class<T> clazz, final StringTranslation<T> translation) {
        this.translationMap.put(clazz, translation);

        return this;
    }

    public BeanCsvReaderBuilder withSeparator(final String separator) {
        this.separator = separator;

        return this;
    }

    public <T> BeanCsvReader<T> build(final InputStream is, final Class<T> clazz) {

        DictionaryCsvReaderFactory dictionaryCsvReaderFactory = new DictionaryCsvReaderFactory();
        DictionaryCsvReader dictReader = dictionaryCsvReaderFactory.getCsvReader(is, this.separator);
        BeanCsvReader<T> csvReader = new BeanCsvReader<T>(dictReader.iterator(), clazz);

        for(final Class<?> type : this.translationMap.keySet()) {
            csvReader.registerTranslator(type, this.translationMap.get(type));
        }

        return csvReader;
    }

    Map<Class<?>, StringTranslation> getTranslationMap() {
        return Collections.unmodifiableMap(this.translationMap);
    }

    private final void appendDefaultTranslations(final Map<Class<?>, StringTranslation> map) {

        map.put(String.class, new StringToStringTranslation());
        map.put(Long.class, new StringToLongTranslation());
        map.put(long.class, new StringToRawLongTranslation());
        map.put(Integer.class, new StringToIntegerTranslation());
        map.put(int.class, new StringToRawIntegerTranslation());
        map.put(Double.class, new StringToDoubleTranslation());
        map.put(double.class, new StringToRawDoubleTranslation());


    }


}
