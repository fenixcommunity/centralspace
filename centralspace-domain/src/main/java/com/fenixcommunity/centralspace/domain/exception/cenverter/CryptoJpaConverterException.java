package com.fenixcommunity.centralspace.domain.exception.cenverter;

import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE, makeFinal = true)
public class CryptoJpaConverterException extends RuntimeException {

    public CryptoJpaConverterException(String message) {
        super(message);
    }

    public CryptoJpaConverterException(String message, Exception e) {
        super(message, e);
    }
}

