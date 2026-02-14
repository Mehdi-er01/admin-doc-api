package com.fsts.document_api.Utils;

public interface TypeConverter<T> {
    T convert(String value) throws Exception;
}
