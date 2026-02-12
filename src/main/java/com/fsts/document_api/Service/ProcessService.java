package com.fsts.document_api.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProcessService {
    private ValidationService valideService;
    private OCRService ocrService;
    private LLMService llmService;
    private PDFService pdfService;

    public ProcessService(ValidationService valideService , OCRService ocrService, LLMService llmService , PDFService pdfService){
        this.valideService=valideService;
        this.ocrService=ocrService;
        this.llmService=llmService;
        this.pdfService = pdfService;
    }
   
    public String processDocument(MultipartFile file) throws Exception {
        if(!valideService.validateDocument(file)){
            throw  new Exception("Document invalide");
        }
        String extractedText = pdfService.extractTextFromPDF(file);
        if (extractedText == null || extractedText.trim().isEmpty()) {
            extractedText = ocrService.performOCR(file);
        }
//        String extractedText = ocrService.performOCR(file);
        String jsonResult = llmService.generateResponse(extractedText);
        if (!valideService.validateLLMResponse(jsonResult)) {
            throw new Exception("JSON invalide généré par le LLM");
        }


        return jsonResult;
    }
}