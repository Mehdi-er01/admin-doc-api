package com.fsts.document_api.Utils;

public class TimeConverter implements TypeConverter<java.time.LocalTime> {
    @Override
    public java.time.LocalTime convert(String value)  {
        try {
            return java.time.LocalTime.parse(value);
        } catch (java.time.format.DateTimeParseException e) {
            throw new RuntimeException("Invalid time format. Expected format is HH:mm:ss.");
        }
    }
    
}
