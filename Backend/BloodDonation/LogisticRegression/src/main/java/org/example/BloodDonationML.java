package org.example;

import java.util.Collections;
import java.util.List;

public class BloodDonationML {
    private final String trainingDataPath;
    private final Utils utils;
    private final LogisticRegression model;
    private final double limit;

    public BloodDonationML(String keyWordsPath, String trainingDataPath, double limit) {
        this.trainingDataPath = trainingDataPath;
        this.utils = new Utils(keyWordsPath);
        this.limit = limit;
        this.model = new LogisticRegression(limit);
    }

    public void bloodDonationMLMain() {
        utils.readTrainingData(trainingDataPath);
        List<DataPoint> dataPointsList = utils.getDataList();
        System.out.println(dataPointsList.size());

        // split the data into training and test data
        // 80% training, 20% test
        Collections.shuffle(dataPointsList);
        int trainSize = (int) (dataPointsList.size() * 0.8);
        List<DataPoint> trainData = dataPointsList.subList(0, trainSize);
        List<DataPoint> testData = dataPointsList.subList(trainSize, dataPointsList.size());

        // train model
        model.train(trainData, 30000, 0.002);

        // test model
        double testPercent = model.evaluate(testData);
        System.out.println("Percent of ML test is: " + testPercent);
    }

    public int predictSentence(String sentence) {
        double keywordPercentage = utils.calculatePercentage(sentence);
        double[] features = {1, keywordPercentage};
        double prediction = model.predict(features);
        System.out.println(prediction);
        return prediction > limit ? 1 : 0;
    }
}
