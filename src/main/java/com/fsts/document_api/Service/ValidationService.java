package com.fsts.document_api.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
//import tools.jackson.databind.JsonNode;
//import tools.jackson.databind.ObjectMapper;
//
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fsts.document_api.Exception.JsonDataMappingException;
import com.fsts.document_api.Exception.RequiredFieldMissingException;
import com.fsts.document_api.Record.DocumentTypeField;


@Service
public class ValidationService {
    private static final Map<String, String> SUPPORTED_MEDIA_TYPES = Map.of(
        "application/pdf", "PDF",
        "image/jpeg", "JPEG",
        "image/png", "PNG"
    );
    
    public boolean validateDocument(MultipartFile file) {
        if(file.isEmpty()){
            return false;
        }

        String filename = file.getOriginalFilename();
        if (filename == null) return false;

        // Autoriser PDF et images
        String extension = filename.toUpperCase().substring(filename.lastIndexOf(".")+1);
        if (!SUPPORTED_MEDIA_TYPES.containsKey(file.getContentType()) ||
            !SUPPORTED_MEDIA_TYPES.values().contains(extension)) {
            return false;
        }

        return true;
    }

    public boolean validateLLMResponse(String response, List<DocumentTypeField> documentFields) {
        try {

            ObjectMapper mapper = new ObjectMapper();
            JsonNode fields = mapper.readTree(response);
            
            for (DocumentTypeField field : documentFields) {
                if (!fields.has(field.name()) || fields.get(field.name()).asText().trim().isEmpty()) {
                    throw new RequiredFieldMissingException("required field '" + field.name() + "' is missing or empty in the LLM response");
                }
            }
            // String dateNaissance = fields.get("date_naissance").asText();
            // if (!dateNaissance.matches("\\d{2}[-./]\\d{2}[-./]\\d{4}")) {
            //     System.out.println("Format de date incorrect : " + dateNaissance);
            //     return false;
            // }
            return true;

        } catch (Exception e) {
            throw new JsonDataMappingException("Invalid JSON response format: " + e.getMessage());
        }

    }
}
