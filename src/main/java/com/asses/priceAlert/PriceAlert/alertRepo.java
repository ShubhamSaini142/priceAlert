package com.asses.priceAlert.PriceAlert;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface alertRepo extends JpaRepository<Alert,Long> {
    List<Alert> findByStatus(String status);


}
