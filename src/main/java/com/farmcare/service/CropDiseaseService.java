package com.farmcare.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.farmcare.model.CropDisease;
import com.farmcare.repository.CropDiseaseRepository;

@Service
public class CropDiseaseService {

    @Autowired
    private CropDiseaseRepository cropDiseaseRepository;

    public CropDisease addDisease(CropDisease disease) {
        return cropDiseaseRepository.save(disease);
    }

    public List<CropDisease> getAllDiseases() {
        return cropDiseaseRepository.findAll();
    }
}