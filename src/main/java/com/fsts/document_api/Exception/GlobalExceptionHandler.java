package com.fsts.document_api.Exception;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidDocumentException.class)
    public ResponseEntity<ErrorResponse> handleInvalidDocumentException(InvalidDocumentException ex) { 
    ErrorResponse error = new ErrorResponse(LocalDateTime.now(),
    HttpStatus.BAD_REQUEST.value(),
    "Invalid Document",
    ex.getMessage());
    return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnsupportedFormatException.class)
    public ResponseEntity<ErrorResponse> handleUnsupportedDocumentException(UnsupportedFormatException ex) {
        ErrorResponse error = new ErrorResponse(LocalDateTime.now(),
        HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(),
        "Unsupported Format",
        ex.getMessage());
        return new ResponseEntity<>(error,HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(RequiredFieldMissingException.class)
    public ResponseEntity<ErrorResponse> handleRequiredFieldMissingException(RequiredFieldMissingException ex) {
        ErrorResponse error = new ErrorResponse(LocalDateTime.now(),
        HttpStatus.BAD_REQUEST.value(),
        "Required Field Missing",
        ex.getMessage());
        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JsonDataMappingException.class)
    public ResponseEntity<ErrorResponse> handleJsonDataMappingException(JsonDataMappingException ex) {
        ErrorResponse error = new ErrorResponse(LocalDateTime.now(),
        HttpStatus.INTERNAL_SERVER_ERROR.value(),
        "JSON Processing Error",
        ex.getMessage());
        return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(FileProcessingException.class)
    public ResponseEntity<ErrorResponse> handleFileProcessingException(FileProcessingException ex) {
        ErrorResponse error = new ErrorResponse(LocalDateTime.now(),
        HttpStatus.INTERNAL_SERVER_ERROR.value(),
        "File Processing Error",
        ex.getMessage());
        return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        ErrorResponse error = new ErrorResponse(LocalDateTime.now(),
        HttpStatus.BAD_REQUEST.value(),
        "Method Argument Not Valid",
        ex.getMessage());
        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        ErrorResponse error = new ErrorResponse(LocalDateTime.now(),
        HttpStatus.INTERNAL_SERVER_ERROR.value(),
        "Internal Server Error",
        ex.getMessage());
        return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
