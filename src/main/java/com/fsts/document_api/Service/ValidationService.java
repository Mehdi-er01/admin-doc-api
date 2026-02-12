package com.fsts.document_api.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Service
public class ValidationService {
    
    public boolean validateDocument(MultipartFile file) {
        if(file.isEmpty()){
            return false;
        }

        String filename = file.getOriginalFilename();
        if (filename == null || !filename.toLowerCase().endsWith(".pdf")) {
            return false;
        }

        return true;

    }

    public boolean validateLLMResponse(String response) {
        try {

            ObjectMapper mapper = new ObjectMapper();
            JsonNode champ = mapper.readTree(response);

            String[] formatJson = {"cin","nom", "prenom", "date_naissance", "adresse"};

            for (String field : formatJson) {
                if (!champ.has(field) || champ.get(field).asText().trim().isEmpty()) {
                    System.out.println("Champ manquant ou vide : " + field);
                    return false;
                }
            }


            String dateNaissance = champ.get("date_naissance").asText();
            if (!dateNaissance.matches("\\d{2}[-./]\\d{2}[-./]\\d{4}")) {
                System.out.println("Format de date incorrect : " + dateNaissance);
                return false;
            }


            return true;

        } catch (Exception e) {
            System.out.println("Réponse JSON invalide : " + e.getMessage());
            return false;
        }

    }
}
