package com.farmcare.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.farmcare.model.MarketPrice;
import com.farmcare.service.MarketPriceService;

@RestController
@RequestMapping("/market-prices")
@CrossOrigin(origins = "*")
public class MarketPriceController {

    @Autowired
    private MarketPriceService marketPriceService;

    @PostMapping
    public MarketPrice addPrice(@RequestBody MarketPrice price) {
        return marketPriceService.addPrice(price);
    }

    @GetMapping
    public List<MarketPrice> getAllPrices() {
        return marketPriceService.getAllPrices();
    }
}