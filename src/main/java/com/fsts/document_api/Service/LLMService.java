package com.fsts.document_api.Service;
import org.springframework.stereotype.Service;

@Service
public class LLMService {
 
    final StringBuilder prompt;

    public LLMService() {
        prompt = new StringBuilder();
        prompt.append("");
    }

    public static String generateResponse(String data) {
        // Implement your LLM response generation logic here
        return null;        
    }



}
