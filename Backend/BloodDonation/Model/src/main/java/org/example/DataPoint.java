package org.example;

public class DataPoint {
    int idTrainingData;
    double keywordPercentage;
    int labelTrainingData;

    public DataPoint(int idTrainingData, double keywordPercentage, int labelTrainingData) {
        this.idTrainingData = idTrainingData;
        this.keywordPercentage = keywordPercentage;
        this.labelTrainingData = labelTrainingData;
    }
}
