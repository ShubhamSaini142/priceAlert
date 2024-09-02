package com.asses.priceAlert.PriceAlert.Indicators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MACDCalculator {

    public double calculateMACD(List<Double> closingPrices) {

        if (closingPrices.size() < 26 + 9) {
            throw new IllegalArgumentException("Not enough data to calculate MACD. Minimum required: " +
                    (26 + 9) + " but got " + closingPrices.size());
        }


        int shortTermPeriod = 12;
        int longTermPeriod = 26;
        int signalPeriod = 9;


        List<Double> shortTermEma = calculateEMAForPeriod(closingPrices, shortTermPeriod);


        List<Double> longTermEma = calculateEMAForPeriod(closingPrices, longTermPeriod);


        List<Double> macdLine = calculateMACDLine(shortTermEma, longTermEma, longTermPeriod);


        List<Double> signalLine = calculateEMAForPeriod(macdLine, signalPeriod);

        List<Double> macdHistogram = calculateMACDHistogram(macdLine, signalLine, signalPeriod);

        double latestMACD = macdHistogram.get(macdHistogram.size() - 1);


        return normalizeMACD(macdHistogram, latestMACD);
    }

    private List<Double> calculateEMAForPeriod(List<Double> prices, int period) {
        List<Double> emaList = new ArrayList<>();
        for (int i = period - 1; i < prices.size(); i++) {
            List<Double> periodPrices = prices.subList(i - period + 1, i + 1);
            emaList.add(calculateEMA(periodPrices, period));
        }
        return emaList;
    }

    private List<Double> calculateMACDLine(List<Double> shortTermEma, List<Double> longTermEma, int longTermPeriod) {
        List<Double> macdLine = new ArrayList<>();
        for (int i = longTermPeriod - 1; i < shortTermEma.size(); i++) {
            double macd = shortTermEma.get(i) - longTermEma.get(i - longTermPeriod + 1);
            macdLine.add(macd);
        }
        return macdLine;
    }

    private List<Double> calculateMACDHistogram(List<Double> macdLine, List<Double> signalLine, int signalPeriod) {
        List<Double> macdHistogram = new ArrayList<>();
        for (int i = signalPeriod - 1; i < macdLine.size(); i++) {
            double histogram = macdLine.get(i) - signalLine.get(i - signalPeriod + 1);
            macdHistogram.add(histogram);
        }
        return macdHistogram;
    }

    private double calculateEMA(List<Double> prices, int period) {
        double multiplier = 2.0 / (period + 1);
        double ema = prices.get(0);
        for (int i = 1; i < prices.size(); i++) {
            ema = ((prices.get(i) - ema) * multiplier) + ema;
        }

        return ema;
    }

    private double normalizeMACD(List<Double> macdHistogram, double latestMACD) {

        double minMACD = Collections.min(macdHistogram);
        double maxMACD = Collections.max(macdHistogram);
        return 1 + (99 * (latestMACD - minMACD) / (maxMACD - minMACD));
    }


}
