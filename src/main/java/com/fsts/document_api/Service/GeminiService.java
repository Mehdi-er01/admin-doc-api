package com.fsts.document_api.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fsts.document_api.Exception.JsonDataMappingException;
import com.fsts.document_api.Record.DocumentTypeField;

import java.util.List;
import java.util.Map;

@Service
public class GeminiService {
    @Autowired
    private Environment env;

    private RestClient restClient = RestClient.create();
    private static final String SYSTEM_PROMPT = """
            ### SYSTEM ROLE
            You are a specialized data extraction AI. Your task is to extract structured information from the raw OCR text of a Moroccan National Identity Card (CIN).

            ### INSTRUCTIONS
            1. Analyze the provided "Raw Text" below.
            2. Extract the following fields strictly.
            3. Fix obvious errors (e.g., if "N0m" appears, read it as "Nom").
            4. Output ONLY valid JSON.

            ### HANDLING ERRORS
            - If a field is not found, set to null.
            """;

    
    
    public String generateResponse(String extractedText, List<DocumentTypeField> documentFields, String model) throws JsonDataMappingException {

        String userMessage = """
                ### TARGET FIELDS
                [
                    """ + String.join(", ", documentFields.stream().map(f -> f.name()).toList()) + """ 
            ]       
                    
                ### RAW OCR TEXT
                """ + extractedText;
        System.out.println(extractedText);
        
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
            .uri("https://generativelanguage.googleapis.com/v1beta/models/" + model + ":generateContent")
            .contentType(MediaType.APPLICATION_JSON)
            .header("x-goog-api-key", env.getProperty("gemini_api_key"))
            .body(requestBody)
            .retrieve()
            .body(String.class);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonRoot = null;
        try {
            jsonRoot = mapper.readTree(rawJson);
        } catch (JsonProcessingException e) {
            throw new JsonDataMappingException("Erreur lors du traitement de la reponse JSON du LLM : " + e.getMessage());
        }
        
        
        String result = jsonRoot.path("candidates")
        .get(0)
        .path("content")
        .path("parts")
        .get(0)
        .path("text")
        .asText();

        System.out.println(result);

        return result;
        
    }

    public String getAvailableModels() {
        return restClient.get()
                .uri("https://generativelanguage.googleapis.com/v1beta/models")
                .header("x-goog-api-key", env.getProperty("gemini_api_key"))
                .retrieve()
                .body(String.class);
    }
}

