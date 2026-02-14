package com.fsts.document_api.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DocumentTypeDTO {
    private Long id;
    private String name;
    private String[] documentFields;
}
