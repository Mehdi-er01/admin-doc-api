package com.fsts.document_api.Entity;

import java.util.List;

import com.fsts.document_api.Utils.StringArrayConverter;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
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
@Table(name = "document_types")
public class DocumentType {
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Lob
    @Convert(converter = StringArrayConverter.class)
    @Column(name = "fields", nullable = false)
    private String[] documentFields;


    @OneToMany(mappedBy = "documentType", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Document> documents;

    
}
