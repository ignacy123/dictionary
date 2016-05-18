package com.github.ignacy123.dictionary;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

/**
 * Created by ignacy on 10.12.15.
 */
public class ApplicationTest {
    private MultiDictionary dictionary;
    private Application application;

    @Before
    public void before() {
        dictionary = mock(MultiDictionary.class);
        application = new Application(dictionary);
    }

    @Test
    public void startReturnsBeginningWords() throws Exception {
        assertThat(application.start(), is("Szukaj tłumaczenia dla: "));
        verifyNoMoreInteractions(dictionary);
    }

    @Test
    public void receiveInputTranslatesApple() {
        when(dictionary.getTranslations("apple")).thenReturn(Arrays.asList("jabłko"));
        assertThat(application.receiveInput("apple"), is("apple = [jabłko]\n"));
        verify(dictionary).getTranslations("apple");
        verifyNoMoreInteractions(dictionary);
    }

    @Test
    public void cutsSpaces() {
        when(dictionary.getTranslations("apple")).thenReturn(Arrays.asList("jabłko"));
        assertThat(application.receiveInput("apple "), is("apple = [jabłko]\n"));
        verify(dictionary).getTranslations("apple");
        verifyNoMoreInteractions(dictionary);
    }

    @Test
    public void receiveInputReturnsInvalidWordText() {
        doThrow(InvalidWordException.class).when(dictionary).getTranslations(anyString());
        assertThat(application.receiveInput("nonExistingWord"), is("Nie znaleziono słowa. Spróbuj ponownie\n"));
        verify(dictionary).getTranslations("nonexistingword");
        verifyNoMoreInteractions(dictionary);
    }

    @Test(expected = ApplicationClosedException.class)
    public void throwsExceptionOnExitCommand() {
        application.receiveInput(Application.COMMAND_EXIT);
        verifyNoMoreInteractions(dictionary);
    }


    @Test
    public void readsCommandParameters() {
        String[] params = application.readCommandParams(Application.COMMAND_ADD, "/add cat kot");
        String[] expectedParams = new String[]{"cat", "kot"};
        assertThat(params, is(expectedParams));
        params = application.readCommandParams(Application.COMMAND_ADD, "/add     cat     kot     ");
        expectedParams = new String[]{"cat", "kot"};
        assertThat(params, is(expectedParams));
        verifyNoMoreInteractions(dictionary);
    }
    @Test
    public void WritesOutWordsInLines(){

    }

}
