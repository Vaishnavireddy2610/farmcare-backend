package com.farmcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.farmcare.model.CropDisease;

public interface CropDiseaseRepository extends JpaRepository<CropDisease, Long> {

}