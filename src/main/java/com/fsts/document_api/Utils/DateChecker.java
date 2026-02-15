package com.fsts.document_api.Utils;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class DateChecker implements TypeChecker {
    @Override
    public boolean isValid(String value) {
        if (value == null || value.trim().isEmpty()) {
            return false;
        }
        try {
            LocalDate.parse(value.trim());
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
