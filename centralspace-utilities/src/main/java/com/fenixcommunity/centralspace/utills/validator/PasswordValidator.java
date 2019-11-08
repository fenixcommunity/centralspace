package com.fenixcommunity.centralspace.utills.validator;


import com.fenixcommunity.centralspace.utills.common.Level;
import com.fenixcommunity.centralspace.utills.exception.validator.PasswordValidatorException;

import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Stream;

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

    public static PasswordValidator lowValidator() {
        PasswordValidator validator = new PasswordValidator();
        validator.level = Level.LOW;
        validator.pattern = PATTERN_LOW;
        return validator;
    }

    public static PasswordValidator highValidator() {
        PasswordValidator validator = new PasswordValidator();
        validator.pattern = PATTERN_HIGH;
        return validator;
    }

    //TODO implementacja, test null
    @Override
    public boolean isValid(Object obj) {
        if (obj instanceof String) {
            return isValid((String) obj);
        }
        return false;
    }

    @Override
    public boolean isValidAll(Object... obj) {
        return Objects.nonNull(obj) && Stream.of(obj).anyMatch(this::isValid);
    }

    @Override
    public void validateWithException(Object obj) {
        if (!isValid(obj)) {
            throw new PasswordValidatorException("Incorrect password on level: " + level.name());
        }
    }

    @Override
    public void validateAllWithException(Object... obj) {
        if (!isValidAll(obj)) {
            throw new PasswordValidatorException("Incorrect passwords on level: " + level.name());
        }
    }

    private boolean isValid(String arg) {
        return pattern.matcher(arg).find();
    }
}
