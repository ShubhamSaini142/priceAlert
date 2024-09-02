package com.asses.priceAlert.PriceAlert.Repository;

import com.asses.priceAlert.PriceAlert.Model.Alert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlertRepo extends JpaRepository<Alert, Long> {
    List<Alert> findByStatus(String status);


}
