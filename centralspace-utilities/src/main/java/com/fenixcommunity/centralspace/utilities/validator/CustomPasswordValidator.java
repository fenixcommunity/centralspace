package com.fenixcommunity.centralspace.utilities.validator;

import static lombok.AccessLevel.PRIVATE;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

import com.fenixcommunity.centralspace.utilities.globalexception.validator.PasswordValidatorException;
import com.google.common.base.Joiner;
import lombok.experimental.FieldDefaults;
import org.passay.AlphabeticalSequenceRule;
import org.passay.DigitCharacterRule;
import org.passay.LengthRule;
import org.passay.NumericalSequenceRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.QwertySequenceRule;
import org.passay.RuleResult;
import org.passay.SpecialCharacterRule;
import org.passay.UppercaseCharacterRule;
import org.passay.WhitespaceRule;

@FieldDefaults(level = PRIVATE)
class CustomPasswordValidator implements Validator {
    private final PasswordValidator validator = new PasswordValidator(Arrays.asList(
            new LengthRule(8, 30),
            new UppercaseCharacterRule(1),
            new DigitCharacterRule(1),
            new SpecialCharacterRule(1),
            new NumericalSequenceRule(3, false),
            new AlphabeticalSequenceRule(3, false),
            new QwertySequenceRule(3, false),
            new WhitespaceRule()));

    @Override
    public boolean isValid(final Object obj) {
        if (obj instanceof char[]) {
            return getValidationResult(obj).isValid();
        }
        return false;
    }

    @Override
    public boolean isValidAll(final Object... obj) {
        return Objects.nonNull(obj) && Stream.of(obj).anyMatch(this::isValid);
    }

    @Override
    public void validateWithException(final Object obj) {
        RuleResult result = getValidationResult(obj);
        if (!result.isValid()) {
            throw new PasswordValidatorException("Incorrect password, broken rules: " + getNotValidResultMessage(result));
        }
    }

    @Override
    public void validateAllWithException(final Object... obj) {
        if (!isValidAll(obj)) {
            throw new PasswordValidatorException("Incorrect passwords for general rules: " + validator.toString());
        }
    }

    RuleResult getValidationResult(final Object arg) {
        if (arg instanceof char[]) {
            return validator.validate(new PasswordData(new String((char[]) arg)));
        }
        return new RuleResult(false);
    }

    String getNotValidResultMessage(final RuleResult result) {
        return Joiner.on(",").join(validator.getMessages(result));
    }
}
