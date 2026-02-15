package com.fsts.document_api.Utils;

public class StringChecker implements TypeChecker {
    @Override
    public boolean isValid(String value) {
        return value != null && !value.trim().isEmpty();
    }
}
