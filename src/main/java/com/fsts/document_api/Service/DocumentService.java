package com.fsts.document_api.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fsts.document_api.Dto.DocumentDTO;
import com.fsts.document_api.Entity.Document;
import com.fsts.document_api.Repository.DocumentRepository;

@Service
public class DocumentService {
    private final DocumentRepository documentRepository;
    private final DocumentTypeService documentTypeService;

    public DocumentService(DocumentRepository documentRepository, DocumentTypeService documentTypeService) {
        this.documentRepository = documentRepository;
        this.documentTypeService = documentTypeService;
    }

    public void saveDocument(DocumentDTO documentDTO) {
        Document document = new Document();
        document.setCreatedAt(LocalDateTime.now());
        document.setContent(documentDTO.getContent());
        document.setDocumentType(documentTypeService.getDocumentTypeByName(documentDTO.getDocumentType()));
        
        documentRepository.save(document);

    }
    public List<DocumentDTO> getAllDocuments() {
        return documentRepository.findAll().stream().map(document -> new DocumentDTO(
            document.getCreatedAt() != null ? document.getCreatedAt().toString() : null,
            document.getContent(),
            document.getDocumentType().getName()
        )).toList();
    }
}
