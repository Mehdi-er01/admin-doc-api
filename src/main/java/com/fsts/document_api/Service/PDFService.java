package com.fsts.document_api.Service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;

@Service
public class PDFService {

    public String extractTextFromPDF(MultipartFile file) throws Exception {

        File convFile = convertMultipartToFile(file);

        PDDocument document = PDDocument.load(convFile);
        PDFTextStripper stripper = new PDFTextStripper();

        String text = stripper.getText(document);

        document.close();

        return text;
    }

    private File convertMultipartToFile(MultipartFile multipartFile) throws Exception {

        File convFile = new File(System.getProperty("java.io.tmpdir")
                + "/" + multipartFile.getOriginalFilename());

        try (FileOutputStream fos = new FileOutputStream(convFile)) {
            fos.write(multipartFile.getBytes());
        }

        return convFile;
    }
}
