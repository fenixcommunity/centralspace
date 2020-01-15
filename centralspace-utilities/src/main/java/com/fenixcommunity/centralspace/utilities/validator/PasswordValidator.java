package com.fenixcommunity.centralspace.utilities.validator;


import com.fenixcommunity.centralspace.utilities.common.Level;
import com.fenixcommunity.centralspace.utilities.exception.validator.PasswordValidatorException;
import lombok.experimental.FieldDefaults;

import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE)
public class PasswordValidator implements Validator {
    // Minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character
    private static final Pattern PATTERN_HIGH = Pattern.compile("^(?=.*?[A-Z])(?=(.*[a-z]){1,})(?=(.*[\\d]){1,})(?=(.*[\\W]){1,})(?!.*\\s).{8,}$");
    // Minimum eight characters, at least one letter and one number
    private static final Pattern PATTERN_LOW = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");

    private Level level = Level.HIGH;
    private Pattern pattern;

    //TODO test i co dać do Validatora?
    private PasswordValidator() {
    }

    static PasswordValidator lowValidator() {
        final PasswordValidator validator = new PasswordValidator();
        validator.level = Level.LOW;
        validator.pattern = PATTERN_LOW;
        return validator;
    }

    static PasswordValidator highValidator() {
        final PasswordValidator validator = new PasswordValidator();
        validator.pattern = PATTERN_HIGH;
        return validator;
    }

    //TODO implementacja, test null
    @Override
    public boolean isValid(final Object obj) {
        if (obj instanceof String) {
            return isValid((String) obj);
        }
        return false;
    }

    @Override
    public boolean isValidAll(final Object... obj) {
        return Objects.nonNull(obj) && Stream.of(obj).anyMatch(this::isValid);
    }

    @Override
    public void validateWithException(final Object obj) {
        if (!isValid(obj)) {
            throw new PasswordValidatorException("Incorrect password on level: " + level.name());
        }
    }

    @Override
    public void validateAllWithException(final Object... obj) {
        if (!isValidAll(obj)) {
            throw new PasswordValidatorException("Incorrect passwords on level: " + level.name());
        }
    }

    private boolean isValid(final String arg) {
        return pattern.matcher(arg).find();
    }
}
