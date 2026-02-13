package com.fsts.document_api.Controller;

import com.fsts.document_api.Service.ProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fsts.document_api.Service.LLMService;
import com.fsts.document_api.Service.OCRService;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
//@CrossOrigin(origins = "http://localhost:5500")
@CrossOrigin(origins = "*") // ou "*" pour tester
public class DocumentController {


    private final ProcessService processService;
    public DocumentController(ProcessService processService){
        this.processService=processService;
    }

    /**

     * @param document : fichier PDF
     * @return JSON généré par le ProcessService
     * @throws Exception 
     * 
     * 
     */
    @PostMapping(value = "/documents", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)

    public ResponseEntity<String> uploadFile (
        @RequestPart("document") MultipartFile document
        // @RequestPart("metadata") String metadata
    ) throws Exception {
    
        // String response = ProcessService.processDocument(document, metadata);
        String response = processService.processDocument(document);
        return ResponseEntity.ok(response);

    }
    @PostMapping(value = "/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadImage(@RequestPart("document") MultipartFile file) {
        try {
            String resultatJson = processService.processImage(file);
            return ResponseEntity.ok(resultatJson);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur lors du traitement de l'image : " + e.getMessage());
        }
    }
}
