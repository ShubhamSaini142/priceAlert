package com.asses.priceAlert.PriceAlert;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBarSeriesBuilder;
import org.ta4j.core.indicators.RSIIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Component
public class WebSocketPriceListener extends WebSocketClient {
   public List<Double> closingPrices = new ArrayList<>();

    public WebSocketPriceListener(String wsUrl) throws URISyntaxException {
        super(new URI(wsUrl));
    }
    @Autowired
    AlertProcessor a = new AlertProcessor();
    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("Connected to Binance");
    }

    @Override
    public void onMessage(String message) {

//        System.out.println("Received message: " + message);

        JsonObject jsonObject = JsonParser.parseString(message).getAsJsonObject();
        double price = jsonObject.get("p").getAsDouble();
        System.out.println("Received message: " + price);
        closingPrices.add(price);

//        System.out.println("Current closingPrices list: " + closingPrices);


        double currentRSI = calculateRSI(closingPrices);
        System.out.println(
                "rsi " + currentRSI
        );


        calculateEMA(closingPrices,12);
        double currentMACD = calculateMACD(closingPrices);


        a.checkAndTriggerAlerts(currentRSI, currentMACD);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Disconnected from Binance WebSocket: " + reason);
    }

    @Override
    public void onError(Exception ex) {
        System.err.println("Error occurred: " + ex.getMessage());
    }

    private double calculateRSI(List<Double> prices) {
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

    private double calculateMACD(List<Double> closingPrices) {



        return 1000;
    }

    public double calculateEMA(List<Double> prices, int period) {
        double multiplier = 2.0 / (period + 1);
        double ema = prices.get(0); // Start with the first price as the initial EMA

        for (int i = 1; i < prices.size(); i++) {
            ema = ((prices.get(i) - ema) * multiplier) + ema;
        }

        return ema;
    }
}
