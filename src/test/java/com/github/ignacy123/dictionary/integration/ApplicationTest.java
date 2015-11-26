package com.github.ignacy123.dictionary.integration;

import com.github.ignacy123.dictionary.take1.Application;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by ignacy on 19.11.15.
 */
public class ApplicationTest {
    @Test
    public void startReturnsBeginningWords() throws Exception {
        Application application = new Application();
        assertThat(application.start(), is("Szukaj tłumaczenia dla: "));
    }

    @Test
    public void receiveInputTranslatesApple() {
        Application application = new Application();
        assertThat(application.receiveInput("apple"), is("apple = jabłko\n"));

    }
    @Test
    public void cutsSpaces() {
        Application application = new Application();
        assertThat(application.receiveInput("apple "), is("apple = jabłko\n"));
    }

    @Test
    public void receiveInputReturnsInvalidWordText() {
        Application application = new Application();
        assertThat(application.receiveInput("nonExistingWord"), is("Nie znaleziono słowa. Spróbuj ponownie\n"));

    }
}
