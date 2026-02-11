package com.fsts.document_api.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ValidationService {
    

    
    public static boolean validateDocument(MultipartFile file) {
        return false;
    }
    public static boolean validateLLMResponse(String response) {
        return false;
    }
}
