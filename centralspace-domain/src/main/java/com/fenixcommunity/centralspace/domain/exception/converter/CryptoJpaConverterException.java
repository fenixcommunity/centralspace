package com.fenixcommunity.centralspace.domain.exception.converter;

import static lombok.AccessLevel.PRIVATE;

import lombok.experimental.FieldDefaults;

@FieldDefaults(level = PRIVATE, makeFinal = true)
public class CryptoJpaConverterException extends RuntimeException {

    public CryptoJpaConverterException(String message) {
        super(message);
    }

    public CryptoJpaConverterException(String message, Exception e) {
        super(message, e);
    }
}