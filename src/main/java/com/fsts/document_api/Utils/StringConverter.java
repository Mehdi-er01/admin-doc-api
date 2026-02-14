package com.fsts.document_api.Utils;

public class StringConverter implements TypeConverter<String> {

    @Override
    public String convert(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new RuntimeException("La valeur ne peut pas être nulle ou vide.");
        }
        return value.trim();
    }
}
