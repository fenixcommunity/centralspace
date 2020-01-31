package com.fenixcommunity.centralspace.utilities.validator;

import static lombok.AccessLevel.PRIVATE;

import java.util.Objects;
import java.util.stream.Stream;

import lombok.experimental.FieldDefaults;
import org.springframework.util.Assert;

@FieldDefaults(level = PRIVATE, makeFinal = true)
public class NotNullValidator implements Validator {

    @Override
    public boolean isValid(final Object object) {
        return Objects.nonNull(object);
    }

    //TODO co jak obj null
    @Override
    public boolean isValidAll(final Object... obj) {
        return Objects.nonNull(obj) && Stream.of(obj).noneMatch(Objects::isNull);
    }

    @Override
    public void validateWithException(final Object object) {
        Assert.notNull(object);
    }

    @Override
    public void validateAllWithException(final Object... obj) {
        Stream.of(obj).forEach(Assert::notNull);
    }
}
