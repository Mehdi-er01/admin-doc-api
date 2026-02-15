package com.fsts.document_api.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fsts.document_api.Dto.DocumentTypeDTO;
import com.fsts.document_api.Entity.Document;
import com.fsts.document_api.Entity.DocumentType;
import com.fsts.document_api.Repository.DocumentTypesRepository;

@Service
public class DocumentTypeService {
    private static DocumentTypesRepository documentTypesRepository;
    public DocumentTypeService(DocumentTypesRepository documentTypesRepository) {
        DocumentTypeService.documentTypesRepository = documentTypesRepository;
    }
    
    public DocumentType getDocumentTypeByName(String name) {
        return documentTypesRepository.findAll()
        .stream()
        .filter(documentType ->{ 
            System.out.println(documentType.getDocumentFields());
            return documentType.getName().equals(name);
        })
        .findFirst()
        .orElseThrow(() -> new RuntimeException("Type de document introuvable : " + name)); 
    }
    
    public List<DocumentTypeDTO> getAllDocumentTypes() {
        return documentTypesRepository
        .findAll()
        .stream()
        .map(documentType -> new DocumentTypeDTO(
            documentType.getId(),
            documentType.getName(),
            documentType.getDocumentFields()
        )).toList();
    }
    public void saveDocumentType(DocumentTypeDTO documentTypeDTO) {
        DocumentType documentType = new DocumentType();
        documentType.setName(documentTypeDTO.getName());
        documentType.setDocumentFields(documentTypeDTO.getDocumentFields());
        documentTypesRepository.save(documentType);
    }
}

