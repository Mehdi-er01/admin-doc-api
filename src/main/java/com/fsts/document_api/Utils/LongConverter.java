package com.fsts.document_api.Utils;

public class LongConverter implements TypeConverter<Long> {

    @Override
    public Long convert(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new RuntimeException("Le champ LONG ne peut pas être nul ou vide.");
        }
        try {
            return Long.parseLong(value.trim());
        } catch (NumberFormatException e) {
            throw new RuntimeException("Le champ LONG doit être un nombre entier valide : " + value);
        }
    }
}
