package com.fsts.document_api.Utils;

import java.math.BigDecimal;

import javax.management.RuntimeErrorException;

public class DecimalConverter implements TypeConverter<BigDecimal>{
    @Override
    public BigDecimal convert(String value) {
        try {
        return new BigDecimal(value);
        } catch (NumberFormatException  e) {
            throw new RuntimeException("Decimal number can't be converted");
        }
    }
}
