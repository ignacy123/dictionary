package com.github.ignacy123.dictionary;

import com.github.ignacy123.dictionary.Dictionary;
import com.github.ignacy123.dictionary.InvalidWordException;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by ignacy on 19.11.15.
 */
public class DictionaryTest {
    @Test
    public void translateTranslatesFruits() {
        Dictionary dictionary = new Dictionary();
        assertThat(dictionary.translate("apple"), is("jabłko"));
        assertThat(dictionary.translate("orange"), is("pomarańcza"));
    }

    @Test(expected = InvalidWordException.class)
    public void translateReturnsNullWhenInvalidWord() throws Exception {
        Dictionary dictionary = new Dictionary();
        dictionary.translate("nonExistingWord");
    }

    @Test
    public void readsTranslationsFile() throws Exception {
        File translationsFile = File.createTempFile("translations", "obj");
        Map<String, String> translations = new HashMap<>();
        translations.put("apple", "jabłko");
        translations.put("orange", "pomarańcza");
        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(translationsFile));
        outputStream.writeObject(translations);
        outputStream.close();
        Dictionary dictionary = new Dictionary(translationsFile);
        assertThat(dictionary.translate("apple"), is("jabłko"));
        assertThat(dictionary.translate("orange"), is("pomarańcza"));
    }
}
