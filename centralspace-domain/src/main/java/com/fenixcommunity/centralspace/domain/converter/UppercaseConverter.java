package com.fenixcommunity.centralspace.domain.converter;

import lombok.experimental.FieldDefaults;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import static lombok.AccessLevel.PRIVATE;

@Converter
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class UppercaseConverter implements AttributeConverter<String, String> {
    @Override
    public String convertToDatabaseColumn(final String attribute) {
        return attribute == null ? null : attribute.toUpperCase();
    }

    @Override
    public String convertToEntityAttribute(final String dbData) {
        return dbData;
    }
}
