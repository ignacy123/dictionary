package com.github.ignacy123.dictionary.take1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by ignacy on 19.11.15.
 */
public class Application {
    private Dictionary dictionary = new Dictionary();

    public String start() {
        return "Szukaj tłumaczenia dla: ";
    }

    public String receiveInput(String input) {
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
        line = line.trim();
        line = line.toLowerCase();
        while (line != null && !line.equals("exit")) {
            String applicationOutput = application.receiveInput(line);
            System.out.print(applicationOutput);
            line = reader.readLine();
            line = line.trim();
            line = line.toLowerCase();
        }
    }
}
