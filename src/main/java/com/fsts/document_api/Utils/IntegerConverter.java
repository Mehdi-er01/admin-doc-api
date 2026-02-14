package com.fsts.document_api.Utils;

public class IntegerConverter implements TypeConverter<Integer> {

    @Override
    public Integer convert(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new RuntimeException("Le champ INTEGER ne peut pas être nul ou vide.");
        }
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            throw new RuntimeException("Le champ INTEGER doit être un nombre entier valide : " + value);
        }
    }
}
