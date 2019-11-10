package com.fenixcommunity.centralspace.domain.exception.cenverter;

public class CryptoJpaConverterException extends RuntimeException {

    public CryptoJpaConverterException(String message) {
        super(message);
    }

    public CryptoJpaConverterException(String message, Exception e) {
        super(message, e);
    }
}

