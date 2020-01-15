package com.fenixcommunity.centralspace.utilities.exception.validator;

import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE, makeFinal = true)
public class PasswordValidatorException extends RuntimeException {
    public PasswordValidatorException(String message) {
        super(message);
    }
}