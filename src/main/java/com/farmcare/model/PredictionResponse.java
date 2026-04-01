package com.farmcare.model;

public class PredictionResponse {
    private String disease;
    private String confidence;
    private String solution;

    public PredictionResponse() {}

    public PredictionResponse(String disease, String confidence, String solution) {
        this.disease = disease;
        this.confidence = confidence;
        this.solution = solution;
    }

    public String getDisease() {
        return disease;
    }
    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getConfidence() {
        return confidence;
    }
    public void setConfidence(String confidence) {
        this.confidence = confidence;
    }

    public String getSolution() {
        return solution;
    }
    public void setSolution(String solution) {
        this.solution = solution;
    }
}
