package com.fsts.document_api.Service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.fsts.document_api.Exception.FileProcessingException;
import java.io.File;
import java.io.FileOutputStream;

@Service
public class PDFService {

    public String extractTextFromPDF(MultipartFile file) throws FileProcessingException {

        String text = "";
        PDFTextStripper stripper= null;
        File convFile = OCRService.convertMultipartToFile(file);
        try(PDDocument document = PDDocument.load(convFile)){
            stripper = new PDFTextStripper();
            text = stripper.getText(document);
        } catch (Exception e) {
            throw new FileProcessingException("Error processing PDF file: " + e.getMessage());
        } finally {
            convFile.delete();
        }
        return text;
    }    
}
