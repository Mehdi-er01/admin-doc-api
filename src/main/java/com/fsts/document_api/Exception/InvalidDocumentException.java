package com.fsts.document_api.Exception;
import lombok.Builder;


public class InvalidDocumentException extends RuntimeException {
    public InvalidDocumentException(String message) {
        super(message);
    }
}
