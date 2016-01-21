package com.github.ignacy123.dictionary;

import com.github.ignacy123.dictionary.Dictionary;
import com.github.ignacy123.dictionary.InvalidWordException;
import org.junit.Test;

import java.io.*;
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
        Map<String, String> translations = createTestTranlations();
        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(translationsFile));
        outputStream.writeObject(translations);
        outputStream.close();
        Dictionary dictionary = new Dictionary(translationsFile);
        assertThat(dictionary.translate("apple"), is("jabłko"));
        assertThat(dictionary.translate("orange"), is("pomarańcza"));
    }

    private Map<String, String> createTestTranlations() {
        Map<String, String> translations = new HashMap<>();
        translations.put("apple", "jabłko");
        translations.put("orange", "pomarańcza");
        return translations;
    }

    @Test
    public void translatesPreviouslyAddedWord() throws Exception {
        Dictionary dictionary = new Dictionary();
        dictionary.addOrSet("cherry", "wiśnia");
        assertThat(dictionary.translate("cherry"), is("wiśnia"));
    }

    @Test
    public void translatesPreviouslyReplacedWord() throws Exception {
        Dictionary dictionary = new Dictionary();
        dictionary.addOrSet("orange", "pomarańczowy");
        assertThat(dictionary.translate("orange"), is("pomarańczowy"));
    }

    @Test
    public void savesWords() throws IOException, ClassNotFoundException {
        Dictionary dictionary = new Dictionary();
        File file = File.createTempFile("translations", ".obj");
        dictionary.save(file);
        Map<String, String> translations = new HashMap<>();
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file))) {
            translations = (Map<String, String>) objectInputStream.readObject();

        }
        Map<String, String> expectedTranslations = createTestTranlations();
        assertThat(translations, is(expectedTranslations));

    }

    @Test
    public void translatesReverse() {
        Dictionary dictionary = new Dictionary();
        dictionary.addOrSet("cherry", "wiśnia");
        assertThat(dictionary.translate("wiśnia"), is("cherry"));
    }
    @Test
    public void ambiguousTranslation(){
        Dictionary dictionary = new Dictionary();
        dictionary.addOrSet("list", "lista");
        dictionary.addOrSet("letter", "list");
        assertThat(dictionary.translate("list"), is("lista"));

    }

}
