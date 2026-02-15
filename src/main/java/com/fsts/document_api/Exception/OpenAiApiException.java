package com.fsts.document_api.Exception;

public class OpenAiApiException extends RuntimeException {
    public OpenAiApiException(String message) {
        super(message);
    }
}
