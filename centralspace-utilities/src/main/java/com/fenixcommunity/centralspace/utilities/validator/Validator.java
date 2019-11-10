package com.fenixcommunity.centralspace.utilities.validator;

public interface Validator {
    //TODO java8 // default
    boolean isValid(Object object);

    //    boolean isValidWithMessage(Object object, String message);
    boolean isValidAll(Object... obj);

    void validateWithException(Object object);

    void validateAllWithException(Object... obj);
}
