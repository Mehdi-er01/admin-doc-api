package com.fsts.document_api.Service;


import org.springframework.web.multipart.MultipartFile;

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
import org.springframework.web.multipart.MultipartFile;

@Service
public class OCRService {
    
    
    public String performOCR(MultipartFile file) throws IOException,TesseractException{

        ITesseract tesseract = new Tesseract();
        File tessDataFolder = loadTessData();
        tesseract.setDatapath(tessDataFolder.getAbsolutePath());
        tesseract.setLanguage("fra");
        String result = tesseract.doOCR(convertMultipartToFile(file));
        //we still need some processing
        //and exception handling we will add it later
        return result;

    }


    private File loadTessData() throws IOException{

    Path tempDir = Files.createTempDirectory("tessdata_temp");
    File tempDirFile = tempDir.toFile();
    tempDirFile.deleteOnExit();

    ClassPathResource resource = new ClassPathResource("tessdata/fra.traineddata");

    if (resource.exists()) {
                File dest = new File(tempDirFile, "fra.traineddata");
                Files.copy(resource.getInputStream(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }
    
    return tempDirFile;
        
    }

    private File convertMultipartToFile(MultipartFile multipartFile) throws IOException {
   
    File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + multipartFile.getOriginalFilename());
    
    try (FileOutputStream fos = new FileOutputStream(convFile)) {
        fos.write(multipartFile.getBytes());
    }
    
    return convFile;
}
}
