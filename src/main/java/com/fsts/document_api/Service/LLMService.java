package com.fsts.document_api.Service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class LLMService {

    private  final StringBuilder prompt = new StringBuilder();
    private  final RestClient restClient;
    private final String apiKey;

    public LLMService(@Value("${gemini.api.url}") String apiUrl,
        @Value("${gemini.api.key}") String apiKey) {

        this.apiKey = apiKey;
        this.restClient = RestClient.builder()
                .baseUrl(apiUrl)
                .build();
        prompt.append(
                """
                        ### SYSTEM ROLE
                        You are a specialized data extraction AI. Your task is to extract structured information from the raw OCR text of a Moroccan National Identity Card (CIN).

                        ### INSTRUCTIONS
                        1. Analyze the provided "Raw OCR Text" below.
                        2. Extract the following fields strictly.
                        3. Fix obvious OCR errors (e.g., if "N0m" appears, read it as "Nom").
                        4. Output ONLY valid JSON. Do not include Markdown formatting (like ```json).

                        ### TARGET SCHEMA
                        {
                          "cin": "String (The alphanumeric ID number, e.g., AB123456)",
                          "nom": "String (First and Last name in Latin characters)",
                          "prenom": "String (First and Last name in Arabic characters, if present)",
                          "date_naissance": "String (DD/MM/YYYY format)",
                          "adresse": "String",
                        }

                        ### HANDLING ERRORS
                        - If a field is not found or unreadable, set the value to null.
                        - If the date is ambiguous, try to standardize it to DD/MM/YYYY.

                        ### RAW OCR TEXT
                        """);

    }

    public String generateResponse(String extractedData) {
        this.prompt.append(extractedData);
        
        
        Map<String, Object> requestBody = Map.of(
            "contents", List.of(
                Map.of("parts", List.of(
                    Map.of("text", this.prompt)
                ))));

        String response = this.restClient.post()
                .uri(uriBuilder -> uriBuilder.queryParam("key", this.apiKey).build())
                .contentType(MediaType.APPLICATION_JSON)
                .body(requestBody)
                .retrieve()
                .body(String.class);

        return response;
    }

}
