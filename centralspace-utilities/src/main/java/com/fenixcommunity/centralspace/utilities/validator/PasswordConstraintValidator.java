package com.fenixcommunity.centralspace.utilities.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.fenixcommunity.centralspace.utilities.adnotation.ValidPassword;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public void initialize(ValidPassword value) {
    }

    @Override
    public boolean isValid(final String password, final ConstraintValidatorContext context) {
        final CustomPasswordValidator customPasswordValidator = new CustomPasswordValidator();
        var validationResult = customPasswordValidator.getValidationResult(password);
        if (validationResult.isValid()) {
            return true;
        }

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(customPasswordValidator.getNotValidResultMessage(validationResult))
                .addConstraintViolation();
        return false;
    }
}