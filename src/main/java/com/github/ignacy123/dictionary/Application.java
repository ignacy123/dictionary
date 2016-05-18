package com.github.ignacy123.dictionary;

import java.io.*;

/**
 * Created by ignacy on 19.11.15.
 */
public class Application {
    public static final String COMMAND_EXIT = "/exit";
    public static final String COMMAND_ADD = "/add";
    public static final String COMMAND_HELP = "/h";
    private MultiDictionary dictionary;

    public String start() {
        return "Szukaj tłumaczenia dla: ";
    }

    public Application(MultiDictionary dictionary) {
        this.dictionary = dictionary;
    }

    public String receiveInput(String input) {
        input = input.trim().toLowerCase();
        switch (input) {
            case COMMAND_EXIT:
                throw new ApplicationClosedException();

            case COMMAND_HELP:
                return ("Lista komend:\n /exit - wyjście\n /add słowo1 słowo2 - dodawanie słów do słownika\n");

            default:

                try {
                    return String.format("%s = %s%n", input, dictionary.getTranslations(input));
                } catch (InvalidWordException e) {
                    return "Nie znaleziono słowa. Spróbuj ponownie\n";
                }

        }

    }

    public static void main(String[] args) throws IOException {
        InputStream inputStream = Application.class.getResourceAsStream("/dictionary.c5");
        MultiDictionary multiDictionary = DictionaryFactory.createDictionaryFromC5InputStream(inputStream);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Application application = new Application(multiDictionary);
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

    public String[] readCommandParams(String command, String input) {
        String paramsString = input.substring(COMMAND_ADD.length());
        paramsString = paramsString.trim();
        return paramsString.split("\\s+");


    }
    public void writeOutWordsInLines(){
        System.out.println(dictionary);
    }
}
