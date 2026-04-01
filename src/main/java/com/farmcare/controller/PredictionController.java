package com.farmcare.controller;

import com.farmcare.model.PredictionResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/predict")
@CrossOrigin(origins = "*")
public class PredictionController {

    @Value("${gemini.api.key:}")
    private String geminiApiKey;

    @PostMapping
    public ResponseEntity<PredictionResponse> predict(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(new PredictionResponse("Error", "0%", "No file uploaded."));
        }

        try {
            // Encode image to Base64
            byte[] bytes = file.getBytes();
            String base64Image = Base64.getEncoder().encodeToString(bytes);
            String mimeType = file.getContentType();
            if (mimeType == null || !mimeType.startsWith("image/")) {
                mimeType = "image/jpeg"; // fallback
            }

            // Fallback rule-based logic if API key is missing (for testing without key)
            if (geminiApiKey == null || geminiApiKey.trim().isEmpty()) {
                String filename = file.getOriginalFilename() != null ? file.getOriginalFilename().toLowerCase() : "";
                if (filename.contains("leaf") || filename.contains("spot")) {
                    return ResponseEntity.ok(new PredictionResponse("Leaf Spot (Rule-Based Mock)", "80%", "Use fungicide correctly."));
                } else if (filename.contains("rust")) {
                    return ResponseEntity.ok(new PredictionResponse("Wheat Rust (Rule-Based Mock)", "95%", "Apply rust fungicide."));
                }
                return ResponseEntity.ok(new PredictionResponse("Unknown Feature (Rule-Based Mock)", "50%", "Could not identify without AI key."));
            }

            // Call Gemini API
            String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + geminiApiKey;
            RestTemplate restTemplate = new RestTemplate();

            Map<String, Object> requestBody = new HashMap<>();
            
            // System instructions
            Map<String, Object> textPart = new HashMap<>();
            textPart.put("text", "You are an expert crop pathologist. Analyze this image. Output ONLY a valid JSON object with exactly exactly 3 string keys: 'disease' (what disease it is, or 'Healthy' if none), 'confidence' (e.g. '92%'), and 'solution' (brief actionable advice). Do NOT wrap the JSON in markdown wrappers like ```json.");

            // Base64 image
            Map<String, Object> inlineData = new HashMap<>();
            inlineData.put("mime_type", mimeType);
            inlineData.put("data", base64Image);
            
            Map<String, Object> imagePart = new HashMap<>();
            imagePart.put("inline_data", inlineData);

            List<Object> parts = new ArrayList<>();
            parts.add(textPart);
            parts.add(imagePart);

            Map<String, Object> contentObj = new HashMap<>();
            contentObj.put("parts", parts);

            List<Object> contentsList = new ArrayList<>();
            contentsList.add(contentObj);
            requestBody.put("contents", contentsList);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

            // Execute POST request
            ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);

            // Parse response
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());
            String responseText = root.path("candidates").get(0).path("content").path("parts").get(0).path("text").asText();
            
            // Map the parsed JSON string directly into PredictionResponse
            PredictionResponse aiResponse = mapper.readValue(responseText, PredictionResponse.class);
            return ResponseEntity.ok(aiResponse);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new PredictionResponse("Analysis Error", "0%", "AI service failed: " + e.getMessage()));
        }
    }
}