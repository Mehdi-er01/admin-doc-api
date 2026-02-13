package com.fsts.document_api.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
//import tools.jackson.databind.JsonNode;
//import tools.jackson.databind.ObjectMapper;
//
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


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
        String lower = filename.toLowerCase();
        if (!(lower.endsWith(".pdf") || lower.endsWith(".jpg") || lower.endsWith(".jpeg") || lower.endsWith(".png"))) {
            return false;
        }

        return true;
    }

    public boolean validateLLMResponse(String response) {
        try {

            ObjectMapper mapper = new ObjectMapper();
            JsonNode champs = mapper.readTree(response);

            String[] formatJson = {"cin","nom", "prenom", "date_naissance", "adresse"};

            for (String champ : formatJson) {
                if (!champs.has(champ) || champs.get(champ).asText().trim().isEmpty()) {
                    System.out.println("Champ manquant ou vide : " + champ);
                    return false;
                }
            }


            String dateNaissance = champs.get("date_naissance").asText();
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
