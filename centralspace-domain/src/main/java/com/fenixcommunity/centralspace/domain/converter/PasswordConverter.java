package com.fenixcommunity.centralspace.domain.converter;

import static lombok.AccessLevel.PRIVATE;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import lombok.experimental.FieldDefaults;

@Converter
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class PasswordConverter implements AttributeConverter<char[], String> {
    @Override
    public String convertToDatabaseColumn(final char[] attribute) {
        return attribute == null ? null : new String(attribute);
    }

    @Override
    public char[] convertToEntityAttribute(final String dbData) {
        return dbData == null ? null : dbData.toCharArray();
    }
}
