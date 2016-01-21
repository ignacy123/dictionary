package com.github.ignacy123.dictionary;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

/**
 * Created by ignacy on 10.12.15.
 */
public class ApplicationTest {
    private Dictionary dictionary;
    private Application application;

    @Before
    public void before() {
        dictionary = mock(Dictionary.class);
//        dictionary = new Dictionary();
//        dictionary = spy(dictionary);
        application = new Application(dictionary);
    }

    @Test
    public void startReturnsBeginningWords() throws Exception {
        assertThat(application.start(), is("Szukaj tłumaczenia dla: "));
        verifyNoMoreInteractions(dictionary);
    }

    @Test
    public void receiveInputTranslatesApple() {
        when(dictionary.translate("apple")).thenReturn("jabłko");
        assertThat(application.receiveInput("apple"), is("apple = jabłko\n"));
        verify(dictionary).translate("apple");
        verifyNoMoreInteractions(dictionary);
    }

    @Test
    public void cutsSpaces() {
        when(dictionary.translate("apple")).thenReturn("jabłko");
        assertThat(application.receiveInput("apple "), is("apple = jabłko\n"));
        verify(dictionary).translate("apple");
        verifyNoMoreInteractions(dictionary);
    }

    @Test
    public void receiveInputReturnsInvalidWordText() {
        doThrow(InvalidWordException.class).when(dictionary).translate(anyString());
        assertThat(application.receiveInput("nonExistingWord"), is("Nie znaleziono słowa. Spróbuj ponownie\n"));
        verify(dictionary).translate("nonexistingword");
        verifyNoMoreInteractions(dictionary);
    }

    @Test(expected = ApplicationClosedException.class)
    public void throwsExceptionOnExitCommand() {
        application.receiveInput(Application.COMMAND_EXIT);
        verifyNoMoreInteractions(dictionary);
    }

    @Test
    public void addsWord() {
        when(dictionary.translate("cat")).thenReturn("kot");
        application.receiveInput("/add cat kot");
        assertThat(application.receiveInput("cat"), is("cat = kot\n"));
        verify(dictionary).addOrSet("cat", "kot");
        verify(dictionary).translate("cat");
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
    @Test(expected = WrongParamsException.class)
    public void checksParams() {
        application.receiveInput("/add error");
        verifyNoMoreInteractions(dictionary);
    }
}
