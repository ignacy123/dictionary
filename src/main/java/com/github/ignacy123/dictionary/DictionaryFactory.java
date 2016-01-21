package com.github.ignacy123.dictionary;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by ignacy on 21.01.16.
 */
public class DictionaryFactory {
    public  MultiDictionary fromC5InputStream(InputStream inputStream) {
        try (Scanner sc = new Scanner(inputStream)) {
            return scanC5InputStream(sc);
        }
    }

    private  MultiDictionary scanC5InputStream(Scanner sc) {
        MultiDictionary multiDictionary = new MultiDictionary();
        outer:
        while (true) {
            while (true) {
                if (!sc.hasNextLine()) {
                    break outer;
                }
                if (sc.nextLine().equals("_____")) {
                    break;
                }
            }
            sc.nextLine();
            String word = sc.nextLine();
            sc.nextLine();
            String translation = "";
            List<String> translations = new ArrayList<>();
            while (sc.hasNextLine() && !(translation = sc.nextLine()).matches("\\s*")) {
                translation = translation.replaceAll("\\d+\\. ", "");
                if (!translation.trim().startsWith("idiom:") && !translation.contains("=")) {
                    translation = translation.replaceAll("<.+>", "");
                    translation = translation.replaceAll("\\(.+\\)", "");

                    translations.add(translation.trim());
                }
            }
            if (!translations.isEmpty()) {
                multiDictionary.addTranslation(word, translations);
            }
        }
        return multiDictionary;
    }
}
