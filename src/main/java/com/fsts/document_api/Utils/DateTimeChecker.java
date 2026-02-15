package com.fsts.document_api.Utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeChecker implements TypeChecker {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    @Override
    public boolean isValid(String value) {
        if (value == null || value.trim().isEmpty()) {
            return false;
        }
        try {
            LocalDateTime.parse(value.trim(), FORMATTER);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
