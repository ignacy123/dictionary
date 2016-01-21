package com.github.ignacy123.dictionary;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ignacy on 21.01.16.
 */
public class MultiDictionary {
    Map<String, List<String>> translations = new HashMap<>();

    public void addTranslation(String word, List<String> wordTranslations) {
        translations.put(word, wordTranslations);
    }
    public List<String> getTranslations(String word){
        return Collections.unmodifiableList(translations.get(word));
    }

}
