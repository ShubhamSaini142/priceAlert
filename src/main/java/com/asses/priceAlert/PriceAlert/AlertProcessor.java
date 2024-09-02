package com.asses.priceAlert.PriceAlert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

@Service
public class AlertProcessor {

    @Autowired
    private AlertRepo alertRepository;

    @Autowired
    NotificationService notificationService;

    private static final Logger logger = Logger.getLogger(AlertProcessor.class.getName());


    public void checkAndTriggerAlerts(double currentRSI, double currentMACD) {
        List<Alert> pendingAlerts = alertRepository.findByStatus("PENDING");

        for (Alert alert : pendingAlerts) {
            boolean triggered = false;
            double currentValue = 0.0;

            if ("RSI".equals(alert.getIndicator())) {
                currentValue = currentRSI;
            } else if ("MACD".equals(alert.getIndicator())) {
                currentValue = currentMACD;
            }

            triggered = checkAlert(alert, currentValue);

            // Log for debugging
            logger.info(String.format("Checking alert: %s, Current value: %.2f, Alert value: %.2f, Triggered: %b",
                    alert.getId(), currentValue, alert.getAlertValue(), triggered));

            if (triggered) {
                alert.setStatus("COMPLETED");
                alert.setTriggeredAt(LocalDateTime.now());
                alertRepository.save(alert);
                notificationService.notifyUser(alert);
            }
        }
    }

    private boolean checkAlert(Alert alert, double currentValue) {
        boolean result = false;
        if ("UP".equals(alert.getDirection())) {
            result = currentValue > alert.getAlertValue();
        } else if ("DOWN".equals(alert.getDirection())) {
            result = currentValue < alert.getAlertValue();
        }
        return result;
    }
}
