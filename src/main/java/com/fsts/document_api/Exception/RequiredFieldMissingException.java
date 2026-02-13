package com.fsts.document_api.Exception;

public class RequiredFieldMissingException extends RuntimeException {
    RequiredFieldMissingException(String message) {
        super(message);
    }
}
