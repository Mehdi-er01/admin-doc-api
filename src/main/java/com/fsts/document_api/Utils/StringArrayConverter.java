package com.fsts.document_api.Utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fsts.document_api.Exception.JsonDataMappingException;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class StringArrayConverter implements AttributeConverter<String[],String>{
    final private ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public String convertToDatabaseColumn(String[] attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new JsonDataMappingException("Can't Map Entity Attribute to Database data" + e.getMessage());
        }
    }

    @Override
    public String[] convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, String[].class);
        } catch (JsonProcessingException e) {
            throw new JsonDataMappingException("Can't Map Database data to Entity Attribute" + e.getMessage());
        }

    }
    
}
