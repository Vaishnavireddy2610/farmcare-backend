package com.farmcare.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.farmcare.model.MarketPrice;
import com.farmcare.repository.MarketPriceRepository;

@Service
public class MarketPriceService {

    @Autowired
    private MarketPriceRepository marketPriceRepository;

    public MarketPrice addPrice(MarketPrice price) {
        return marketPriceRepository.save(price);
    }

    public List<MarketPrice> getAllPrices() {
        return marketPriceRepository.findAll();
    }
}