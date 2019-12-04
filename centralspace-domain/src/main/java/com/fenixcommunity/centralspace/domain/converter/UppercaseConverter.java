package com.fenixcommunity.centralspace.domain.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class UppercaseConverter implements AttributeConverter<String, String> {
    @Override
    public String convertToDatabaseColumn(String attribute) {
        return attribute == null ? null : attribute.toUpperCase();
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return dbData;
    }
}
