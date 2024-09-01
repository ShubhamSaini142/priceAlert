package com.asses.priceAlert.PriceAlert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class alertProcessor {

    @Autowired
    private alertRepo alertRepository;

    public void checkAndTriggerAlerts(double currentRSI, double currentMACD) {
        List<Alert> pendingAlerts = alertRepository.findByStatus("PENDING");

        for (Alert alert : pendingAlerts) {
            boolean triggered = false;

            if ("RSI".equals(alert.getIndicator())) {
                triggered = checkAlert(alert, currentRSI);
            } else if ("MACD".equals(alert.getIndicator())) {
                triggered = checkAlert(alert, currentMACD);
            }

            if (triggered) {
                alert.setStatus("COMPLETED");
                alert.setTriggeredAt(LocalDateTime.now());
                alertRepository.save(alert);
                NotificationService.notifyUser(alert);
            }
        }
    }

    private boolean checkAlert(Alert alert, double currentValue) {
        if ("UP".equals(alert.getDirection())) {
            return currentValue > alert.getValue();
        } else {
            return currentValue < alert.getValue();
        }
    }
}
