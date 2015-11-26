package com.github.ignacy123.dictionary.take1;

import java.util.HashMap;

/**
 * Created by ignacy on 19.11.15.
 */
public class Dictionary {
    private final HashMap<String, String> words = new HashMap<String, String>();

    public Dictionary() {
        words.put("apple", "jabłko");
        words.put("orange", "pomarańcza");
    }

    public String translate(String word) {
        if (words.containsKey(word)) {
            return words.get(word);
        }
        throw new InvalidWordException();
    }
}
