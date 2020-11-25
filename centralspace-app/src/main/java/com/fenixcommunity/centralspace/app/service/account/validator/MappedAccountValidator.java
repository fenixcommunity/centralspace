package com.fenixcommunity.centralspace.app.service.account.validator;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.fenixcommunity.centralspace.domain.model.permanent.account.Account;
import org.springframework.validation.annotation.Validated;

@Validated
public class MappedAccountValidator implements ConstraintValidator<ValidMappedAccount, Account> {

    @Override
    public void initialize(ValidMappedAccount constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(final Account account, final ConstraintValidatorContext context) {
        if (account == null || (account.getLogin() == null || account.getMail() == null)) {
            return false;
        }

        return true;
    }
}