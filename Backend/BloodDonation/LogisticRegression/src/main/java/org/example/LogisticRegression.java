package org.example;

import java.util.List;

public class LogisticRegression {
    private double[] weights;
    private double limit;

    public LogisticRegression(double limit) {
        weights = new double[2]; // One for the feature and one for the intercept (bias)
        this.limit = limit;
    }

    private double sigmoid(double z) {
        return 1 / (1 + Math.exp(-z));
    }

    public void train(List<DataPoint> trainData, int epochs, double learningRate) {
        for (int epoch = 0; epoch < epochs; epoch++) {
            for (DataPoint data : trainData) {
                double[] features = {1, data.keywordPercentage};
                double predicted = predict(features);
                double error = data.labelTrainingData - predicted;
                for (int j = 0; j < weights.length; j++) {
                    weights[j] += learningRate * error * predicted * (1 - predicted) * features[j];
                }
            }
        }
    }

    public double evaluate(List<DataPoint> testData) {
        int correct = 0;
        for (DataPoint data : testData) {
            double[] features = {1, data.keywordPercentage};
            double predicted = predict(features);
            int predictedLabel = (predicted > limit) ? 1 : 0;
            if (predictedLabel == data.labelTrainingData) {
                correct++;
            }
        }
        return (double) correct / testData.size();
    }

    public double predict(double[] features) {
        double z = weights[0];
        for (int i = 1; i < weights.length; i++) {
            z += weights[i] * features[i];
        }
        return sigmoid(z);
    }
}

