package com.fsts.document_api.Utils;

public class TextChecker implements TypeChecker {
    @Override
    public boolean isValid(String value) {
        return value != null && !value.trim().isEmpty();
    }
}
