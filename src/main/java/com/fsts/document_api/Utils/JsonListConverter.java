package com.fsts.document_api.Utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fsts.document_api.Exception.JsonDataMappingException;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import com.fsts.document_api.Record.DocumentTypeField;
import java.util.List;

@Converter
public class JsonListConverter implements AttributeConverter<List<DocumentTypeField>, String> {
    final private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<DocumentTypeField> attribute) {
        if (attribute == null || attribute.isEmpty()) {
            return "[]";
        }
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new JsonDataMappingException("Impossible de mapper l'attribut de l'entite vers les donnees de la base : " + e.getMessage());
        }
    }

    @Override
    public List<DocumentTypeField> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return List.of();
        }
        try {
            return objectMapper.readValue(dbData,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, DocumentTypeField.class));
        } catch (JsonProcessingException e) {
            throw new JsonDataMappingException("Impossible de mapper les donnees de la base vers l'attribut de l'entite : " + e.getMessage());
        }

    }

}

