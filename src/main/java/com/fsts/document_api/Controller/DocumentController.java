package com.fsts.document_api.Controller;

import com.fsts.document_api.Service.ProcessService;

import java.util.List;

import javax.print.Doc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fsts.document_api.Dto.DocumentDTO;
import com.fsts.document_api.Dto.DocumentTypeDTO;
import com.fsts.document_api.Entity.DocumentType;
import com.fsts.document_api.Exception.InvalidDocumentException;
import com.fsts.document_api.Service.DocumentService;
import com.fsts.document_api.Service.DocumentTypeService;
import com.fsts.document_api.Service.LLMService;
import com.fsts.document_api.Service.OCRService;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api")
public class DocumentController {
    private final DocumentTypeService documentTypeService;
    private final DocumentService documentService;
    private final ProcessService processService;
    DocumentController(DocumentTypeService documentTypeService,
         DocumentService documentService,
         ProcessService processService){

        this.documentTypeService=documentTypeService;
        this.documentService=documentService;
        this.processService=processService;
    }


    
    @PostMapping(value = "/documents", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)

    public ResponseEntity<String> uploadFile (
        @RequestPart("document") MultipartFile document,
        @RequestPart("type") String type
    ) throws InvalidDocumentException, Exception  {
    
        String response = processService.processDocument(document, type);
        return ResponseEntity.ok(response);

    }
    @GetMapping("/documents/types")
    public ResponseEntity<List<DocumentTypeDTO>> getDocumentTypes() {
        List<DocumentTypeDTO> types = documentTypeService.getAllDocumentTypes();
        return ResponseEntity.ok(types);
        
    }
    @PostMapping("/documents/types")
    public ResponseEntity<List<DocumentTypeDTO>> addDocumentType(
        @RequestBody DocumentTypeDTO documentTypeDTO
    ) {
        documentTypeService.saveDocumentType(documentTypeDTO);
        return ResponseEntity.ok(documentTypeService.getAllDocumentTypes());
    }

    @GetMapping("/documents/all")
    public ResponseEntity<List<DocumentDTO>> getDocument() {
        List<DocumentDTO> documents = documentService.getAllDocuments();
        return ResponseEntity.ok(documents);
    }
    
    

    // @GetMapping("/documents")
    // public ResponseEntity<List<String>> getDocuments() {
    //     List<String> documents = DocumentService.getAllDocuments();
    //     return ResponseEntity.ok(documents);
    // }

    // @GetMapping("/models")
    // public String getModels() {
    //     return LLMService.getAvailableModels();
    // }
    // @GetMapping("/documents/types")
    // public String getDocumentTypes() {
    //     return DocumentService.getDocumentTypes();
        
    // }
    // @PostMapping("/documents/types")
    // public ResponseEntity<String> addDocumentType(@RequestParam String type) {
    //     DocumentService.addDocumentType(type);
    //     return ResponseEntity.ok("Type de document ajouté : " + type);
    // }
}
