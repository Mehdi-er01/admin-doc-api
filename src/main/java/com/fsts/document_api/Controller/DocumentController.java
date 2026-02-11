package com.fsts.document_api.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fsts.document_api.Service.OCRService;
import com.fsts.document_api.Service.ProcessService;

@RestController
@RequestMapping("/api")
public class DocumentController {

    

    @Autowired OCRService ocr;
    @PostMapping(value = "/documents", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadFile (
        @RequestPart("document") MultipartFile document
        // @RequestPart("metadata") String metadata
    ) {
    
        // String response = ProcessService.processDocument(document, metadata);
        try {
            return ResponseEntity.ok(ocr.performOCR(document));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error processing document: " + e.getMessage());
        }
        
    }
}
