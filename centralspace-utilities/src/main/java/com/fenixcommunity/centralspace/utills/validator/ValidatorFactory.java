package com.fenixcommunity.centralspace.utills.validator;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


// @Component???
//TODO @Profile(ProductionProfiles.REAL_INFRASTRUCTURE)
@Component
public class ValidatorFactory {
    //TODO rozszerz o DataValidations w TLK
    // Component? Singleton? return DataValidations.getInstance();
    // https://www.baeldung.com/spring-assert

    private Map<ValidatorType, Validator> cache = new ConcurrentHashMap<>();

    private Validator initValidator(ValidatorType type) {
        Validator validator = null;
        if (type == ValidatorType.NOT_NULL) {
            validator = new NotNullValidator();
        } else if (type == ValidatorType.PASSWORD_LOW) {
            validator = PasswordValidator.lowValidator();
        } else if (type == ValidatorType.PASSWORD_HIGH) {
            validator = PasswordValidator.highValidator();
        }

        if (validator != null) {
            registerValidator(type, validator);
        }

        return validator;
    }

    private void registerValidator(ValidatorType type, Validator validator) {
        cache.put(type, validator);
    }

    public Validator getInstance(ValidatorType type) {
//TODO        usun Assert   / Level level
        boolean df = true;
        validateInstanceExist(type);
        Validator validator = cache.get(type);
        return validator == null ? initValidator(type) : validator;
    }

    //to test
    public void validateInstanceExist(ValidatorType type) {
        Assert.notNull(type, "ValidatorType cannot be null");
    }

}