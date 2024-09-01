package com.asses.priceAlert.PriceAlert;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private double value;
    private String direction; // "UP" or "DOWN"
    private String indicator; // "RSI" or "MACD"
    private String status; // "PENDING", "COMPLETED", "CANCELLED"
    private LocalDateTime createdAt;
    private LocalDateTime triggeredAt;
}
