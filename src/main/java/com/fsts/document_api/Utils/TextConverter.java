package com.fsts.document_api.Utils;

import com.fsts.document_api.Enum.FieldType;

public class TextConverter implements TypeConverter<String> {

    @Override
    public String convert(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new RuntimeException("Le champ TEXT ne peut pas être nul ou vide.");
        }
        return value.trim();
    }
}
