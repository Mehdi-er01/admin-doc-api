package com.fsts.document_api.Utils;

import java.math.BigDecimal;

public class DecimalChecker implements TypeChecker {
    @Override
    public boolean isValid(String value) {
        if (value == null || value.trim().isEmpty()) {
            return false;
        }
        try {
            new BigDecimal(value.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
