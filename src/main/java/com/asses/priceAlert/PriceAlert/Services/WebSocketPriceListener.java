package com.asses.priceAlert.PriceAlert.Services;

import com.asses.priceAlert.PriceAlert.Indicators.MACDCalculator;
import com.asses.priceAlert.PriceAlert.Indicators.RSICalculator;
import com.asses.priceAlert.PriceAlert.Services.AlertService;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


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
    AlertService a = new AlertService();

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("Connected to Binance");
    }

    @Override
    public void onMessage(String message) {

//        System.out.println("Received message: " + message);

        JsonObject jsonObject = JsonParser.parseString(message).getAsJsonObject();
        double price = jsonObject.get("x").getAsDouble();

        closingPrices.add(price);
        System.out.println("Received message: " + price + " Data added to list " + closingPrices.size());

if(closingPrices.size() > 100) {
    RSICalculator rsiCalculator = new RSICalculator();
    double currentRSI = rsiCalculator.calculateRSI(closingPrices);
    System.out.println(
            "rsi " + currentRSI
    );

    MACDCalculator calculator = new MACDCalculator();
    double currentMACD = calculator.calculateMACD(closingPrices);

    System.out.println("MACD " + currentMACD);



    a.checkAndTriggerAlerts(currentRSI, currentMACD);

}
else {
    System.out.println("Data is Filling Wait for few secs.....");
}



    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Disconnected from Binance WebSocket: " + reason);
    }

    @Override
    public void onError(Exception ex) {
        System.err.println("Error occurred: " + ex.getMessage());
    }


}

