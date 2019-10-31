package com.fenixcommunity.centralspace.utills.validator;

import org.springframework.util.Assert;

import java.util.Objects;
import java.util.stream.Stream;

public class AssertValidator implements Validator{

    @Override
    public boolean isValid(Object object) {
        return Objects.nonNull(object);
    }
//TODO co jak obj null
    @Override
    public boolean isValidAll(Object... obj) {
        return Objects.nonNull(obj) && Stream.of(obj).noneMatch(Objects::isNull);
    }

    @Override
    public void validateWithException(Object object) {
        Assert.notNull(object);
    }

    @Override
    public void validateAllWithException(Object... obj) {
        Stream.of(obj).forEach(Assert::notNull);
    }
}
