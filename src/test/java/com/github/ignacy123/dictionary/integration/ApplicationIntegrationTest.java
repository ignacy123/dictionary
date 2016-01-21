package com.github.ignacy123.dictionary.integration;

import com.github.ignacy123.dictionary.Application;
import com.github.ignacy123.dictionary.ApplicationClosedException;
import com.github.ignacy123.dictionary.Dictionary;
import com.github.ignacy123.dictionary.WrongParamsException;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by ignacy on 19.11.15.
 */
public class ApplicationIntegrationTest {
    private Application application;

    @Before
    public void before() {
        application = new Application(new Dictionary());
    }

    @Test
    public void startReturnsBeginningWords() throws Exception {
        assertThat(application.start(), is("Szukaj tłumaczenia dla: "));
    }

    @Test
    public void receiveInputTranslatesApple() {
        assertThat(application.receiveInput("apple"), is("apple = jabłko\n"));

    }

    @Test
    public void cutsSpaces() {
        assertThat(application.receiveInput("apple "), is("apple = jabłko\n"));
    }

    @Test
    public void receiveInputReturnsInvalidWordText() {
        assertThat(application.receiveInput("nonExistingWord"), is("Nie znaleziono słowa. Spróbuj ponownie\n"));

    }

    @Test(expected = ApplicationClosedException.class)
    public void throwsExceptionOnExitCommand() {
        application.receiveInput(Application.COMMAND_EXIT);

    }

    @Test
    public void addsWord() {
        application.receiveInput("/add cat kot");
        assertThat(application.receiveInput("cat"), is("cat = kot\n"));
    }

    @Test
    public void readsCommandParameters() {
        String[] params = application.readCommandParams(Application.COMMAND_ADD, "/add cat kot");
        String[] expectedParams = new String[]{"cat", "kot"};
        assertThat(params, is(expectedParams));
        params = application.readCommandParams(Application.COMMAND_ADD, "/add     cat     kot     ");
        expectedParams = new String[]{"cat", "kot"};
        assertThat(params, is(expectedParams));

    }

    @Test(expected = WrongParamsException.class)
    public void checksParams() {
        application.receiveInput("/add error");
    }


}
