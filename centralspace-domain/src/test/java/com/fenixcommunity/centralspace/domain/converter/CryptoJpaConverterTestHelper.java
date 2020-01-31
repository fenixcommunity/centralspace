package com.fenixcommunity.centralspace.domain.converter;

import static org.apache.commons.lang3.StringUtils.isEmpty;

import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ArgumentConverter;


class CryptoJpaConverterTestHelper implements ArgumentConverter {

    private CryptoJpaConverter cryptoJpaConverter = new CryptoJpaConverter();

    @Override
    public Object convert(Object source, ParameterContext parameterContext) throws ArgumentConversionException {
        if (!(source instanceof String)) {
            throw new IllegalArgumentException("The argument should be a string: " + source);
        }

        String password = (String) source;
        String decryptedResult = cryptoJpaConverter.convertToDatabaseColumn(password);
        if (isEmpty(decryptedResult) || password.equals(decryptedResult)) {
            return false;
        }
        String encryptedResult = cryptoJpaConverter.convertToEntityAttribute(decryptedResult);
        return !isEmpty(encryptedResult) && password.equals(encryptedResult);
    }
}

