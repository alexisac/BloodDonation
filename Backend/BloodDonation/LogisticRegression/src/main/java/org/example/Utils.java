package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    KeywordChecker keywordChecker;
    List<DataPoint> dataList;

    public Utils(String keyWordsPath) {
        keywordChecker = new KeywordChecker(readKeyWords(keyWordsPath));
        dataList = new ArrayList<>();
    }

    public void readTrainingData(String filePath) {
        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split("~ ");
                if(tokens.length == 3) {
                    int id = Integer.parseInt(tokens[0].trim());
                    String phrase = tokens[1].trim();
                    int label = Integer.parseInt(tokens[2].trim());
                    double percentage = calculatePercentage(phrase);
                    dataList.add(new DataPoint(id, percentage, label));
                }
            }
        } catch (IOException ex) {
//            String error = String.valueOf(ex);
        }
    }

    public List<String> readKeyWords(String filePath) {
        List<String> data = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                data.add(line);
            }
        } catch (IOException ex) {
//            String error = String.valueOf(ex);
        }
        return data;
    }


    public double calculatePercentage(String phrase){
        int foundWords = keywordChecker.containsKeyword(phrase);
        int totalWords = phrase.split("\\s+").length;
        return (double) foundWords / totalWords;
    }

    public List<DataPoint> getDataList() {
        return dataList;
    }
}
