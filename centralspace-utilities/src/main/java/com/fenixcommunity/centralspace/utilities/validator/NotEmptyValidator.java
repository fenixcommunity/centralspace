package com.fenixcommunity.centralspace.utilities.validator;

import static lombok.AccessLevel.PRIVATE;
import static org.apache.commons.collections4.MapUtils.isNotEmpty;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import lombok.experimental.FieldDefaults;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.util.Assert;

@FieldDefaults(level = PRIVATE, makeFinal = true)
class NotEmptyValidator implements Validator {

    @Override
    public boolean isValid(final Object object) {
        if (object instanceof String) {
            return Strings.isNotEmpty((String) object);
        } else if (object instanceof Collection) {
            return CollectionUtils.isNotEmpty((Collection) object);
        } else if (object instanceof Map) {
            return isNotEmpty((Map) object);
        }
        throw new IllegalArgumentException("Validator provided only for String, Collection, Map!");
    }

    @Override
    public boolean isValidAll(final Object... obj) {
        return Objects.nonNull(obj) && Stream.of(obj).noneMatch(this::isValid);
    }

    @Override
    public void validateWithException(final Object object) {
        Assert.isTrue(isValid(object));
    }

    @Override
    public void validateAllWithException(final Object... obj) {
        Stream.of(obj).forEach(object -> Assert.isTrue(isValid(object)));
    }
}