package com.fsts.document_api.Service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fsts.document_api.Enum.FieldType;
import com.fsts.document_api.Exception.JsonDataMappingException;
import com.fsts.document_api.Exception.RequiredFieldMissingException;
import com.fsts.document_api.Record.DocumentTypeField;
import com.fsts.document_api.Utils.BooleanChecker;
import com.fsts.document_api.Utils.DateChecker;
import com.fsts.document_api.Utils.DateTimeChecker;
import com.fsts.document_api.Utils.DecimalChecker;
import com.fsts.document_api.Utils.IntegerChecker;
import com.fsts.document_api.Utils.LongChecker;
import com.fsts.document_api.Utils.StringChecker;
import com.fsts.document_api.Utils.TextChecker;
import com.fsts.document_api.Utils.TimeChecker;
import com.fsts.document_api.Utils.TypeChecker;

@Service
public class ValidationService {
    private static final Map<String, String> SUPPORTED_MEDIA_TYPES = Map.of(
            "application/pdf", "PDF",
            "image/jpeg", "JPEG",
            "image/png", "PNG");

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final Map<FieldType, TypeChecker> typeCheckers = Map.of(
            FieldType.STRING, new StringChecker(),
            FieldType.TEXT, new TextChecker(),
            FieldType.INTEGER, new IntegerChecker(),
            FieldType.LONG, new LongChecker(),
            FieldType.DECIMAL, new DecimalChecker(),
            FieldType.BOOLEAN, new BooleanChecker(),
            FieldType.DATE, new DateChecker(),
            FieldType.TIME, new TimeChecker(),
            FieldType.DATETIME, new DateTimeChecker());

    public boolean validateDocument(MultipartFile file) {
        if (file.isEmpty()) {
            return false;
        }

        String filename = file.getOriginalFilename();
        if (filename == null || !filename.contains(".")) {
            return false;
        }

        String extension = filename.toUpperCase().substring(filename.lastIndexOf('.') + 1);
        if (!SUPPORTED_MEDIA_TYPES.containsKey(file.getContentType()) ||
                !SUPPORTED_MEDIA_TYPES.values().contains(extension)) {
            return false;
        }

        return true;
    }

    public boolean validateLLMResponse(String response, List<DocumentTypeField> documentFields) {
        try {
            JsonNode fields = objectMapper.readTree(response);

            for (DocumentTypeField field : documentFields) {
                if (!fields.hasNonNull(field.name()) || fields.get(field.name()).asText().trim().isEmpty()) {
                    throw new RequiredFieldMissingException(
                            "le champ requis '" + field.name() + "' est manquant ou vide dans la reponse du LLM");
                }

                String rawValue = fields.get(field.name()).asText();
                TypeChecker checker = typeCheckers.get(field.type());
                if (checker == null) {
                    throw new JsonDataMappingException("Aucun verificateur trouve pour le type de champ : " + field.type());
                }

                if (!checker.isValid(rawValue)) {
                    throw new JsonDataMappingException(
                            "valeur invalide pour le champ '" + field.name() + "'. type attendu : " + field.type().getValue());
                }
            }

            return true;
        } catch (RequiredFieldMissingException | JsonDataMappingException e) {
            throw e;
        } catch (Exception e) {
            throw new JsonDataMappingException("Format de reponse JSON invalide : " + e.getMessage());
        }
    }
}

