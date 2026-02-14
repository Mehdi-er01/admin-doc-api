package com.fsts.document_api.Utils;

public class BooleanConverter implements TypeConverter<Boolean> {
    @Override
    public Boolean convert(String value)  {
        if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
            return Boolean.parseBoolean(value);
        } else {
            throw new RuntimeException("Invalid boolean format. Expected 'true' or 'false'.");
        }
    }
    
}
