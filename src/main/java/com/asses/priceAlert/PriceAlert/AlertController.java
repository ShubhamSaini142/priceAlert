package com.asses.priceAlert.PriceAlert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/alerts")
public class AlertController {

    @Autowired
    private alertRepo alertRepository;

    @PostMapping
    public Alert createAlert(@RequestBody Alert alert) {
        alert.setStatus("PENDING");
        alert.setCreatedAt(LocalDateTime.now());
        return alertRepository.save(alert);
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

