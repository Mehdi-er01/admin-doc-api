package com.fsts.document_api.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fsts.document_api.Entity.Document;

@Repository
public interface DocumentRepository extends JpaRepository<Document,Long>{
    
}
