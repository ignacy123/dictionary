package com.github.ignacy123.dictionary;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ignacy on 19.11.15.
 */
public class Dictionary {
    private final Map<String, String> words = new HashMap<String, String>();

    public Dictionary() {
        words.put("apple", "jabłko");
        words.put("orange", "pomarańcza");
    }

    public void add(String a, String b) {
        words.put(a, b);
    }

    public Dictionary(File translationsFile) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(translationsFile))) {
            Map<String, String> translations = (Map<String, String>) inputStream.readObject();
            words.putAll(translations);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String translate(String word) {
        if (words.containsKey(word)) {
            return words.get(word);
        }
        throw new InvalidWordException();
    }

    public void addOrSet(String word, String translation) {
        words.put(word, translation);
    }

    public void save(File file) throws FileNotFoundException {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file))) {
            outputStream.writeObject(words);
        } catch (IOException e) {
            throw new FileNotFoundException();
        }

    }
}
