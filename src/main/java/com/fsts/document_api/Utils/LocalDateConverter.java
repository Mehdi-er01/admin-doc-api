package com.fsts.document_api.Utils;

public class LocalDateConverter implements TypeConverter<java.time.LocalDate> {
    @Override
    public java.time.LocalDate convert(String value)  {
        try {
            return java.time.LocalDate.parse(value);
        } catch (java.time.format.DateTimeParseException e) {
            throw new RuntimeException("Invalid date format. Expected format is yyyy-MM-dd.");
        }
    }
    
}
