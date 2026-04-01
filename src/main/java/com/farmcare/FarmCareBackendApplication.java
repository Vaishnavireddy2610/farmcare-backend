package com.farmcare;

import com.farmcare.model.MarketPrice;
import com.farmcare.service.MarketPriceService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.util.List;

@SpringBootApplication
public class FarmCareBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(FarmCareBackendApplication.class, args);
    }

    @Bean
    public CommandLineRunner initMarketPrices(MarketPriceService marketPriceService) {
        return args -> {
            List<MarketPrice> currentPrices = marketPriceService.getAllPrices();
            if (currentPrices == null || currentPrices.isEmpty()) {
                System.out.println("Seeding database with sample Market Prices...");
                marketPriceService.addPrice(new MarketPrice(null, "Wheat", 2200, "Hyderabad", "2026-03-01"));
                marketPriceService.addPrice(new MarketPrice(null, "Wheat", 2350, "Warangal", "2026-03-05"));
                marketPriceService.addPrice(new MarketPrice(null, "Rice", 3100, "Hyderabad", "2026-03-01"));
                marketPriceService.addPrice(new MarketPrice(null, "Rice", 2950, "Nizamabad", "2026-03-03"));
                marketPriceService.addPrice(new MarketPrice(null, "Maize", 1800, "Karimnagar", "2026-03-02"));
                marketPriceService.addPrice(new MarketPrice(null, "Tomato", 1200, "Hyderabad", "2026-03-04"));
                marketPriceService.addPrice(new MarketPrice(null, "Cotton", 6500, "Adilabad", "2026-03-01"));
                marketPriceService.addPrice(new MarketPrice(null, "Sugarcane", 3500, "Nalgonda", "2026-03-02"));
                marketPriceService.addPrice(new MarketPrice(null, "Soybean", 4200, "Warangal", "2026-03-03"));
                System.out.println("Seeding completed!");
            }
        };
    }
}
