package com.fsts.document_api.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fsts.document_api.Entity.DocumentType;
import com.fsts.document_api.Exception.InvalidDocumentException;

@Service
public class ProcessService {
    private ValidationService valideService;
    private DocumentTypeService documentTypeService;
    private OCRService ocrService;
    private LLMService llmService;
    private PDFService pdfService;

    public ProcessService(ValidationService valideService,
            DocumentTypeService documentTypeService,
            OCRService ocrService, LLMService llmService,
            PDFService pdfService) {

        this.valideService = valideService;
        this.documentTypeService = documentTypeService;
        this.ocrService = ocrService;
        this.llmService = llmService;
        this.pdfService = pdfService;
    }

    public String processDocument(MultipartFile file, String type) throws InvalidDocumentException, Exception {

        if (!valideService.validateDocument(file)) {
            throw new InvalidDocumentException("invalid file for document processing");
        }
        String extractedText = null;
        if (file.getContentType() != null && file.getContentType().startsWith("image/")) {
            extractedText = ocrService.performOCR(file);

        } else if (file.getContentType().equals("application/pdf")) {
             extractedText = pdfService.extractTextFromPDF(file);
        }
        if (extractedText == null || extractedText.trim().isEmpty()) {
            throw new InvalidDocumentException("no text extracted from the fjle");
        }

        String[] documentFields = documentTypeService
                .getDocumentTypeByName(type)
                .getDocumentFields();

        String jsonResult = llmService.generateResponse(extractedText, documentFields);
        if (!valideService.validateLLMResponse(jsonResult, documentFields)) {
            throw new Exception("JSON invalide généré par le LLM");
        }

        return jsonResult;
    }



}