package com.fsts.document_api.Record;

import com.fsts.document_api.Enum.FieldType;

public record DocumentTypeField(
    String name,
    FieldType type
) {}
