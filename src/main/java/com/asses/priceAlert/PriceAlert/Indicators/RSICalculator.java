package com.asses.priceAlert.PriceAlert.Indicators;

import java.util.List;

public class RSICalculator {

    public double calculateRSI(List<Double> prices) {
        int period = 14;
        if (prices.size() < period + 1) {
            throw new IllegalArgumentException("Not enough data to calculate RSI. Required: " + (period + 1) + ", but got: " + prices.size());
        }


        double gainSum = 0;
        double lossSum = 0;

        for (int i = 1; i <= period; i++) {
            double change = prices.get(i) - prices.get(i - 1);
            if (change > 0) {
                gainSum += change;
            } else {
                lossSum -= change;
            }
        }

        double averageGain = gainSum / period;
        double averageLoss = lossSum / period;

        if (averageLoss == 0) {
            return 100;
        }

        double rs = averageGain / averageLoss;
        double rsi = 100 - (100 / (1 + rs));

        for (int i = period + 1; i < prices.size(); i++) {
            double change = prices.get(i) - prices.get(i - 1);

            if (change > 0) {
                averageGain = ((averageGain * (period - 1)) + change) / period;
                averageLoss = (averageLoss * (period - 1)) / period;
            } else {
                averageGain = (averageGain * (period - 1)) / period;
                averageLoss = ((averageLoss * (period - 1)) - change) / period;
            }

            rs = averageGain / averageLoss;
            rsi = 100 - (100 / (1 + rs));
        }

        return rsi;

    }

}
