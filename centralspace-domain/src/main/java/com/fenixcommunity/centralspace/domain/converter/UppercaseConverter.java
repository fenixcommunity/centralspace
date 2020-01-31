package com.fenixcommunity.centralspace.domain.converter;

import static lombok.AccessLevel.PRIVATE;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import lombok.experimental.FieldDefaults;

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
