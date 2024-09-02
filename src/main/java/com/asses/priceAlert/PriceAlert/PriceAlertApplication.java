package com.asses.priceAlert.PriceAlert;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.net.URISyntaxException;

@SpringBootApplication
public class PriceAlertApplication {

	public static void main(String[] args) {
		SpringApplication.run(PriceAlertApplication.class, args);
	}
	@Bean
	public CommandLineRunner run(WebSocketPriceListener listener) {
		return args -> {
			listener.run();
		};
	}

	@Bean
	public WebSocketPriceListener webSocketPriceListener() throws URISyntaxException {
		String wsUrl ="wss://stream.binance.com:9443/ws/btcusdt@ticker";
		return new WebSocketPriceListener(wsUrl);
	}
}
