package com.fsts.document_api.Service;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fsts.document_api.Dto.DocumentDTO;
import com.fsts.document_api.Entity.DocumentType;
import com.fsts.document_api.Exception.InvalidDocumentException;
import com.fsts.document_api.Record.DocumentTypeField;



@Service
public class ProcessService {
    private ValidationService valideService;
    private DocumentTypeService documentTypeService;
    private OCRService ocrService;
    private OpenAiService llmService;
    private PDFService pdfService;
    private DocumentService documentService;
    private ObjectMapper objectMapper = new ObjectMapper();

    public ProcessService(ValidationService valideService,
            DocumentTypeService documentTypeService,
            OCRService ocrService, OpenAiService llmService,
            PDFService pdfService, DocumentService documentService) {

        this.valideService = valideService;
        this.documentTypeService = documentTypeService;
        this.ocrService = ocrService;
        this.llmService = llmService;
        this.documentService = documentService;
        this.pdfService = pdfService;
    }

    public String processDocument(MultipartFile file, String type, String model) throws InvalidDocumentException, Exception {

        if (!valideService.validateDocument(file)) {
            throw new InvalidDocumentException("fichier invalide pour le traitement du document");
        }
        String extractedText = null;
        if (file.getContentType() != null && file.getContentType().startsWith("image/")) {
            extractedText = ocrService.performOCR(file);

        } else if (file.getContentType().equals("application/pdf")) {
             extractedText = pdfService.extractTextFromPDF(file);
        }
        if (extractedText == null || extractedText.trim().isEmpty()) {
            throw new InvalidDocumentException("aucun texte extrait du fichier");
        }

        List<DocumentTypeField> documentFields = documentTypeService
                .getDocumentTypeByName(type)
                .getDocumentFields();

        String jsonResult = llmService.generateResponse(extractedText, documentFields, model);
        if (!valideService.validateLLMResponse(jsonResult, documentFields)) {
            throw new Exception("JSON invalide genere par le LLM");
        }
        Map<String, String> content = objectMapper.readValue(
                  jsonResult,
                  new TypeReference<Map<String, String>>() {}
          );

        documentService.saveDocument(new DocumentDTO(null, content, type));
        return jsonResult;
    }



}
