package com.fsts.document_api.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;

final record GeminiRequest(List<Content> contents) {}
final record Content(List<Part> parts) {}
final record Part(String text) {}


@Service
public class LLMService {
    @Autowired
    private Environment env;

    private RestClient restClient = RestClient.create();
    // private final String apiKey;
    // private final String model;
    // private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String SYSTEM_PROMPT = """
            ### SYSTEM ROLE
            You are a specialized data extraction AI. Your task is to extract structured information from the raw OCR text of a Moroccan National Identity Card (CIN).

            ### INSTRUCTIONS
            1. Analyze the provided "Raw OCR Text" below.
            2. Extract the following fields strictly.
            3. Fix obvious OCR errors (e.g., if "N0m" appears, read it as "Nom").
            4. Output ONLY valid JSON.

            ### TARGET SCHEMA
            {
              "cin": "String",
              "nom": "String",
              "prenom": "String",
              "date_naissance": "String (DD/MM/YYYY)",
              "adresse": "String"
            }

            ### HANDLING ERRORS
            - If a field is not found, set to null.
            """;

    

    public String generateResponse(String ocrText) throws JsonMappingException, JsonProcessingException {

        String userMessage = "### RAW OCR TEXT\n" + ocrText;
        Map<String,Object> requestBody = Map.of("contents",List.of(
            Map.of("role", "user","parts",List.of(Map.of("text",userMessage)))
        ),
        "generationConfig", Map.of(
        "response_mime_type", "application/json",
        "temperature", 0.2),
        "systemInstruction",Map.of(
        "parts", List.of(Map.of("text", SYSTEM_PROMPT))
    ));

        String rawJson = restClient.post()
            .uri("https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent")
            .contentType(MediaType.APPLICATION_JSON)
            .header("x-goog-api-key", env.getProperty("gemini_api_key"))
            .body(requestBody)
            .retrieve()
            .body(String.class);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonRoot = mapper.readTree(rawJson);
        
        String result = jsonRoot.path("candidates")
        .get(0)
        .path("content")
        .path("parts")
        .get(0)
        .path("text")
        .asText();


        return result;
        
    }
}
