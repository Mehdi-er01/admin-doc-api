package com.fsts.document_api.Service;

import org.springframework.web.multipart.MultipartFile;

import com.fsts.document_api.Exception.FileProcessingException;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;


@Service
public class OCRService {
    
    
    public String performOCR(MultipartFile file){

        ITesseract tesseract = new Tesseract();
        File tessDataFolder = loadTessData();
        tesseract.setDatapath(tessDataFolder.getAbsolutePath());
        tesseract.setLanguage("fra");
        String result;
        try {
            result = tesseract.doOCR(convertMultipartToFile(file));
        } catch (TesseractException e) {
            throw new RuntimeException("Error performing OCR: " + e.getMessage());
        }
        
        return result;

    }


    private File loadTessData() throws FileProcessingException {

    Path tempDir;
    try {
        tempDir = Files.createTempDirectory("tessdata_temp");
    } catch (IOException e) {
        throw new FileProcessingException("Error creating temporary directory for tessdata: " + e.getMessage());
    }
    File tempDirFile = tempDir.toFile();
    tempDirFile.deleteOnExit();

    ClassPathResource resource = new ClassPathResource("tessdata/fra.traineddata");

    if (resource.exists()) {
                File dest = new File(tempDirFile, "fra.traineddata");
                
                try{
                    Files.copy(resource.getInputStream(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    throw new FileProcessingException("Error copying tessdata file: " + e.getMessage());
                }
    }
    
    return tempDirFile;
        
    }

    private File convertMultipartToFile(MultipartFile multipartFile) throws FileProcessingException {
   
    File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + multipartFile.getOriginalFilename());
    
    try (FileOutputStream fos = new FileOutputStream(convFile)) {
        fos.write(multipartFile.getBytes());
    } catch(IOException e) {
        throw new FileProcessingException("Error converting multipart file to file: " + e.getMessage());
    }
    
    return convFile;
}
}
