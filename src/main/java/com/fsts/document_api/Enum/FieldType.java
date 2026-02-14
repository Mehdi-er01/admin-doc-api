package com.fsts.document_api.Enum;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fsts.document_api.Exception.RequiredFieldMissingException;

public enum FieldType {
      STRING("string"),
      TEXT("text"),
      INTEGER("integer"),
      LONG("long"),
      DECIMAL("decimal"),
      BOOLEAN("boolean"),
      DATE("date"),
      TIME("time"),
      DATETIME("datetime");

      private final String value;

      FieldType(String value) {
            this.value = value;
      }
      @JsonValue
      public String getValue() {
          return value;
      }

      @JsonCreator
      public static FieldType fromValue(String raw) {
          if (raw == null) {
              throw new RequiredFieldMissingException("FieldType cannot be null");
          }
          for (FieldType type : values()) {
              if (type.value.equalsIgnoreCase(raw.trim())) {
                  return type;
              }
          }
          throw new IllegalArgumentException("Unknown FieldType: " + raw);
      }
  }
