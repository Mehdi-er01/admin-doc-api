package com.fsts.document_api.Service;

import java.util.List;
import java.util.Map;

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

@Service
public class OpenAiService {

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

    
    public String generateResponse(String extractedText, List<DocumentTypeField> documentFields, String model)
            throws JsonDataMappingException {

        String userMessage = """
                ### TARGET FIELDS
                [
                    """ + String.join(", ", documentFields.stream().map(f -> f.name()).toList()) + """
                ]

                    ### RAW OCR TEXT
                    """ + extractedText;

        Map<String, Object> requestBody = Map.of("model", model, "input" , List.of(
                Map.of("role", "system", "content", SYSTEM_PROMPT),
                Map.of("role", "user", "content", userMessage)
        ));

        String rawJson = restClient.post()
                .uri("https://api.openai.com/v1/responses")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + env.getProperty("openai_api_key"))
                .body(requestBody)
                .retrieve()
                .body(String.class);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonRoot = null;
        try {
            jsonRoot = mapper.readTree(rawJson);
        } catch (JsonProcessingException e) {
            throw new JsonDataMappingException(
                    "Erreur lors du traitement de la reponse JSON du LLM : " + e.getMessage());
        }

        String result = jsonRoot.path("content")
                .get(0)
                .path("text")
                .get(0)
                .asText();

        return result;

    }


    public String getAvailableModels() {
        
        String rawJson = restClient.get()
                .uri("https://api.openai.com/v1/models")
                .header("Authorization", "Bearer " + env.getProperty("openai_api_key"))
                .retrieve()
                .body(String.class);

        return rawJson;
    }

}
