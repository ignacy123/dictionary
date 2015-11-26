package com.github.ignacy123.dictionary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by ignacy on 19.11.15.
 */
public class Application {
    public static final String COMMAND_EXIT = "/exit";
    private Dictionary dictionary = new Dictionary();

    public String start() {
        return "Szukaj tłumaczenia dla: ";
    }

    public String receiveInput(String input) {
        input = input.trim().toLowerCase();
        if (input.equals(COMMAND_EXIT)) {
            throw new ApplicationClosedException();
        }
        try {
            return String.format("%s = %s%n", input, dictionary.translate(input));
        } catch (InvalidWordException e) {
            return "Nie znaleziono słowa. Spróbuj ponownie\n";
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Application application = new Application();
        System.out.println(application.start());
        String line = reader.readLine();
        while (true) {
            try {
                String applicationOutput = application.receiveInput(line);
                System.out.print(applicationOutput);
            } catch (ApplicationClosedException e) {
                break;
            }

            line = reader.readLine();
        }
    }
}
