package com.asses.priceAlert.PriceAlert;

import org.springframework.stereotype.Service;

    @Service
    public class NotificationService {

        public static void notifyUser(Alert alert) {
            System.out.println("Notification: Alert triggered for user " +
                    " - Indicator: " + alert.getIndicator() + " has crossed the value " + alert.getAlertValue() + " Direction is " + alert.getDirection());
        }
    }


