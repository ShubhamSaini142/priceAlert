package com.asses.priceAlert.PriceAlert;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
@Component
public class WebSocketPriceListener extends WebSocketClient {

    public WebSocketPriceListener(String wsUrl) throws URISyntaxException {
        super(new URI(wsUrl));
    }
    @Autowired
    alertProcessor a = new alertProcessor();
    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("Connected to Binance WebSocket");
    }

    @Override
    public void onMessage(String message) {

        JsonObject jsonObject = JsonParser.parseString(message).getAsJsonObject();
        double price = jsonObject.get("p").getAsDouble();
        System.out.println("Received message: " + price);

        // Here you would calculate indicators like RSI and MACD
        double currentRSI = calculateRSI(price);
        double currentMACD = calculateMACD(price);
        // Process alerts based on the current RSI and MACD

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

    private double calculateRSI(double price) {
        // Dummy implementation - replace with actual RSI calculation
        return price / 1000;
    }

    private double calculateMACD(double price) {
        // Dummy implementation - replace with actual MACD calculation
        return price / 10000;
    }
}
