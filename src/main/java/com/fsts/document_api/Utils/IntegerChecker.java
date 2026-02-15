package com.fsts.document_api.Utils;

public class IntegerChecker implements TypeChecker {
    @Override
    public boolean isValid(String value) {
        if (value == null || value.trim().isEmpty()) {
            return false;
        }
        try {
            Integer.parseInt(value.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
