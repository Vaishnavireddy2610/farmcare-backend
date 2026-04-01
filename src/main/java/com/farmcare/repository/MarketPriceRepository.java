package com.farmcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.farmcare.model.MarketPrice;

public interface MarketPriceRepository extends JpaRepository<MarketPrice, Long> {

}