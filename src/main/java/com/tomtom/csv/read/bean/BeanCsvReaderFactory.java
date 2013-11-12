package com.tomtom.csv.read.bean;

import com.tomtom.csv.read.bean.translation.StringToDoubleTranslation;
import com.tomtom.csv.read.bean.translation.StringToIntegerTranslation;
import com.tomtom.csv.read.bean.translation.StringToLongTranslation;
import com.tomtom.csv.read.bean.translation.StringToStringTranslation;
import com.tomtom.csv.read.dictionary.DictionaryCsvReader;
import com.tomtom.csv.read.dictionary.DictionaryCsvReaderFactory;

import java.io.InputStream;


public class BeanCsvReaderFactory {

    public BeanCsvReaderFactory() {

    }


    public <T> BeanCsvReader<T> getBeanCsvReader(final InputStream inputStream, final String separator, final Class<T> clazz) {

        DictionaryCsvReaderFactory dictionaryFactory = new DictionaryCsvReaderFactory();
        DictionaryCsvReader dictionaryReader = dictionaryFactory.getCsvReader(inputStream, separator);

        BeanCsvReader<T> result = new BeanCsvReader<T>(dictionaryReader.iterator(), clazz);

        result.registerTranslator(String.class, new StringToStringTranslation());
        result.registerTranslator(Long.class, new StringToLongTranslation());
        result.registerTranslator(long.class, new StringToLongTranslation());
        result.registerTranslator(Integer.class, new StringToIntegerTranslation());
        result.registerTranslator(int.class, new StringToIntegerTranslation());
        result.registerTranslator(Double.class, new StringToDoubleTranslation());
        result.registerTranslator(double.class, new StringToDoubleTranslation());

        return result;
    }

}
