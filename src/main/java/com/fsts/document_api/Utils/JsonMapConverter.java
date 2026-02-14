package com.fsts.document_api.Utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fsts.document_api.Exception.JsonDataMappingException;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Converter(autoApply = true) 
public class JsonMapConverter implements AttributeConverter<Map<String, String>, String> {

    
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Map<String, String> attribute) {
        if (attribute == null) {
            return "{}";
        }
        try {
            return mapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new JsonDataMappingException("Can't Map Entity Attribute to Database data: " + e.getMessage());
        }
    }

    @Override
    public Map<String, String> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return new HashMap<>();
        }
        try {
            
            return mapper.readValue(dbData, new TypeReference<Map<String, String>>() {});
        } catch (IOException e) {
            return new HashMap<>();
        }
    }
}