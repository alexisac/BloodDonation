package org.example;

import java.util.HashSet;
import java.util.List;

/*
 * HashSet because it offers searches/insertions/deletions in O(1) time
* */

public class KeywordChecker {
    private HashSet<String> keywords;

    public KeywordChecker(List<String> keywordsList) {
        this.keywords = new HashSet<>(keywordsList);
    }

    public int containsKeyword(String sentence) {
        int wordsNumber = 0;
        String[] words = sentence.split("\\s+"); // split by whitespace
        for(String word: words) {
            word = word.replaceAll("[^a-zA-Z0-9 ]", ""); // Remove punctuation
            if(keywords.contains(word.toLowerCase())) {
                wordsNumber++;
            }
        }
        return wordsNumber;
    }
}
