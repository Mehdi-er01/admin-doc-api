package com.fsts.document_api.Utils;

public class LongChecker implements TypeChecker {
    @Override
    public boolean isValid(String value) {
        if (value == null || value.trim().isEmpty()) {
            return false;
        }
        try {
            Long.parseLong(value.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
