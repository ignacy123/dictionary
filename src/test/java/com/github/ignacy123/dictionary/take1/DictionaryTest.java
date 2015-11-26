package com.github.ignacy123.dictionary.take1;

import org.junit.Test;

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
}
