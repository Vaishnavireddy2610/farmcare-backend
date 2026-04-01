package com.farmcare.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.farmcare.model.CropDisease;
import com.farmcare.service.CropDiseaseService;

@RestController
@RequestMapping("/diseases")
@CrossOrigin(origins = "*")
public class CropDiseaseController {

    @Autowired
    private CropDiseaseService cropDiseaseService;

    @PostMapping
    public CropDisease addDisease(@RequestBody CropDisease disease) {
        return cropDiseaseService.addDisease(disease);
    }

    @GetMapping
    public List<CropDisease> getAllDiseases() {
        return cropDiseaseService.getAllDiseases();
    }
}