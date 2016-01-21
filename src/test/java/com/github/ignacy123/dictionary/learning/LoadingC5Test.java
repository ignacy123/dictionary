package com.github.ignacy123.dictionary.learning;

import com.github.ignacy123.dictionary.Dictionary;
import com.github.ignacy123.dictionary.DictionaryFactory;
import com.github.ignacy123.dictionary.MultiDictionary;
import org.junit.Test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by ignacy on 21.01.16.
 */
public class LoadingC5Test {
    @Test
    public void testLoadingC5() {
        InputStream inputStream = getClass().getResourceAsStream("/dictionary.c5");
        DictionaryFactory dictionaryFactory = new DictionaryFactory();
        MultiDictionary multiDictionary = dictionaryFactory.fromC5InputStream(inputStream);
        List<String> expectedTranslation = Arrays.asList("zostawiać, opuszczać", "zaprzestawać", "rzucać");
        assertThat(multiDictionary.getTranslations("abandon"), is(expectedTranslation));
    }
}
