package com.fenixcommunity.centralspace.utilities.exception.validator;

import static lombok.AccessLevel.PRIVATE;

import lombok.experimental.FieldDefaults;

@FieldDefaults(level = PRIVATE, makeFinal = true)
public class PasswordValidatorException extends RuntimeException {
    public PasswordValidatorException(String message) {
        super(message);
    }
}