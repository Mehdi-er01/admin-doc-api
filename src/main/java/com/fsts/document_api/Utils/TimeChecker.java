package com.fsts.document_api.Utils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TimeChecker implements TypeChecker {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Override
    public boolean isValid(String value) {
        if (value == null || value.trim().isEmpty()) {
            return false;
        }
        try {
            LocalTime.parse(value.trim(), FORMATTER);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
