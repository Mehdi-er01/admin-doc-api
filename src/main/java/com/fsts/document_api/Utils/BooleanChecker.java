package com.fsts.document_api.Utils;

public class BooleanChecker implements TypeChecker {
    @Override
    public boolean isValid(String value) {
        if (value == null) {
            return false;
        }
        String normalized = value.trim();
        return normalized.equalsIgnoreCase("true") || normalized.equalsIgnoreCase("false");
    }
}
