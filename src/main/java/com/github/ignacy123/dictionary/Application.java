package com.github.ignacy123.dictionary;

import java.io.*;

/**
 * Created by ignacy on 19.11.15.
 */
public class Application {
    public static final String COMMAND_EXIT = "/exit";
    public static final String COMMAND_ADD = "/add";
    public static final String COMMAND_HELP = "/h";
    private Dictionary dictionary = new Dictionary();

    public String start() {
        return "Szukaj tłumaczenia dla: ";
    }

    public String receiveInput(String input) {
        input = input.trim().toLowerCase();
        switch (input) {
            case COMMAND_EXIT:
                throw new ApplicationClosedException();

            case COMMAND_HELP:
                return ("Lista komend:\n /exit - wyjście\n /add słowo1 słowo2 - dodawanie słów do słownika\n");

            default:
                if (input.startsWith(COMMAND_ADD)) {
                    String[] params = this.readCommandParams(COMMAND_ADD, input);
                    if(params.length<2){
                        throw new WrongParamsException();
                    }
                    dictionary.add(params[0], params[1]);
                    return "Dodano słowo.";
                }
                try {
                    return String.format("%s = %s%n", input, dictionary.translate(input));
                } catch (InvalidWordException e) {
                    return "Nie znaleziono słowa. Spróbuj ponownie\n";
                }

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

    public String[] readCommandParams(String command, String input) {
        String paramsString = input.substring(COMMAND_ADD.length());
        paramsString = paramsString.trim();
        return paramsString.split("\\s+");

    }
}
