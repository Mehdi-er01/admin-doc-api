package com.fsts.document_api.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fsts.document_api.Entity.DocumentType;
@Repository
public interface DocumentTypesRepository extends JpaRepository<DocumentType, Long> {
 
} 