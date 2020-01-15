package com.fenixcommunity.centralspace.utilities.validator;

import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static lombok.AccessLevel.PRIVATE;


// @Component???
//TODO @Profile(ProductionProfiles.REAL_INFRASTRUCTURE)
@Component
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ValidatorFactory {
    //TODO rozszerz o DataValidations w TLK
    // Component? Singleton? return DataValidations.getInstance();
    // https://www.baeldung.com/spring-assert

    private final Map<ValidatorType, Validator> cache = new ConcurrentHashMap<>();

    private Validator initValidator(final ValidatorType type) {
        Validator validator = null;
        if (type == ValidatorType.NOT_NULL) {
            validator = new NotNullValidator();
        } else if (type == ValidatorType.PASSWORD_LOW) {
            validator = PasswordValidator.lowValidator();
        } else if (type == ValidatorType.PASSWORD_HIGH) {
            validator = PasswordValidator.highValidator();
        } else if (type == ValidatorType.MAIL) {
            validator = new MailValidator();
        }

        if (validator != null) {
            registerValidator(type, validator);
        }

        return validator;
    }

    private void registerValidator(final ValidatorType type, Validator validator) {
        cache.put(type, validator);
    }

    public Validator getInstance(final ValidatorType type) {
//TODO        usun Assert   / Level level
        validateInstanceExist(type);
        final Validator validator = cache.get(type);
        return validator == null ? initValidator(type) : validator;
    }

    //to test
    public void validateInstanceExist(final ValidatorType type) {
        Assert.notNull(type, "ValidatorType cannot be null");
    }

}