package com.asses.priceAlert.PriceAlert;

import org.springframework.stereotype.Service;

    @Service
    public class NotificationService {

        public static void notifyUser(Alert alert) {
            // For simplicity, we'll just print the notification to the console
            System.out.println("Notification: Alert triggered for user " + alert.getUserId() +
                    " - Indicator: " + alert.getIndicator() + " has crossed the value " + alert.getValue());
        }
    }


