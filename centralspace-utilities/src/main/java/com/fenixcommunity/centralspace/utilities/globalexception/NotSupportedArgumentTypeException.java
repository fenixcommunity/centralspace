package com.fenixcommunity.centralspace.utilities.globalexception;

import static lombok.AccessLevel.PRIVATE;

import lombok.experimental.FieldDefaults;

@FieldDefaults(level = PRIVATE, makeFinal = true)
public class NotSupportedArgumentTypeException extends RuntimeException {

    public NotSupportedArgumentTypeException(String providedArgumentType) {
        super(messageWrapper(providedArgumentType));
    }

    public NotSupportedArgumentTypeException(String providedArgumentType, Throwable cause) {
        super(messageWrapper(providedArgumentType), cause);
    }

    private static String messageWrapper(String providedArgumentType) {
        return String.format("Provided type {%s} is not supported", providedArgumentType);
    }

}
