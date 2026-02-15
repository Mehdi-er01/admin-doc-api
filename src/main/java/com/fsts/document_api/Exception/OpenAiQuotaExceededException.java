package com.fsts.document_api.Exception;

public class OpenAiQuotaExceededException extends RuntimeException {
    public OpenAiQuotaExceededException(String message) {
        super(message);
    }
}
