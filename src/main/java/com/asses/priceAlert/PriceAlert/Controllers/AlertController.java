package com.asses.priceAlert.PriceAlert.Controllers;

import com.asses.priceAlert.PriceAlert.Model.Alert;
import com.asses.priceAlert.PriceAlert.Repository.AlertRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/alerts")
public class AlertController {

    @Autowired
    private AlertRepo alertRepository;

    @PostMapping
    public ResponseEntity<?> createAlert(@RequestBody Alert alert) {
        if (!"UP".equals(alert.getDirection()) && !"DOWN".equals(alert.getDirection())) {
            return ResponseEntity.badRequest().body("Please use Correct Direction");
        }
        if (!"RSI".equals(alert.getIndicator()) && !"MACD".equals(alert.getIndicator())) {
            return ResponseEntity.badRequest().body("Please Use Correct Indicator");
        }
        alert.setStatus("PENDING");
        alert.setCreatedAt(LocalDateTime.now());
        return ResponseEntity.ok(alertRepository.save(alert));
    }

    @GetMapping("/{id}")
    public Optional<Alert> getAlert(@PathVariable Long id) {
        return alertRepository.findById(id);
    }

    @GetMapping
    public List<Alert> getAllAlerts() {
        return alertRepository.findAll();
    }


}

