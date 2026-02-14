package com.fsts.document_api.Utils;

public class DateConverter implements TypeConverter<java.util.Date> {
    @Override
    public java.util.Date convert(String value)  {
        try {
            return new java.text.SimpleDateFormat("yyyy-MM-dd").parse(value);
        } catch (java.text.ParseException e) {
            throw new RuntimeException("Invalid date format. Expected format is yyyy-MM-dd.");
        }
    }
    
}
