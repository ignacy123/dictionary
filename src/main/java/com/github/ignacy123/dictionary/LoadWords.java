package com.github.ignacy123.dictionary;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Created by ignacy on 20.01.16.
 */
public class LoadWords {
    private static final Pattern WORD_PATTERN = Pattern.compile("\\w*-\\w*\\n");
    private File file;
    private String words;
    public void loadWords(File file) throws FileNotFoundException {
        Scanner sc = new Scanner(words);
        words = file.toString();
        String word = sc.next(WORD_PATTERN);
        String word1 = "";
        String word2 = "";
        //word.split(word.indexOf("-"));

    }
}
