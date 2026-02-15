package com.fsts.document_api.Entity;

import java.time.LocalDateTime;
import java.util.Map;

import javax.print.Doc;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fsts.document_api.Dto.DocumentDTO;
import com.fsts.document_api.Utils.JsonMapConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "documents")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "content")
    @Lob
    @Convert(converter = JsonMapConverter.class)
    private Map<String, String> content;
    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "type_id")
    private DocumentType documentType;

    
    }
