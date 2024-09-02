package com.asses.priceAlert.PriceAlert.Services;

import com.asses.priceAlert.PriceAlert.Model.Alert;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {


    public void notifyUser(Alert alert) {
        System.out.println("Notification: Alert triggered for user " +
                " - Indicator: " + alert.getIndicator() + " has crossed the value " + alert.getAlertValue() + " Direction is " + alert.getDirection());

        String title = "Alert Triggered: " + alert.getIndicator();
        String message = "Indicator: " + alert.getIndicator() +
                " has crossed the value " + alert.getAlertValue() +
                " in direction " + alert.getDirection();
    }


}
