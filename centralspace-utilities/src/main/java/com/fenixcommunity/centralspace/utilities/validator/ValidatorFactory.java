package com.fenixcommunity.centralspace.utilities.validator;

import static lombok.AccessLevel.PRIVATE;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;


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
        } else if (type == ValidatorType.PASSWORD_CUSTOM) {
            validator = new CustomPasswordValidator();
        } else if (type == ValidatorType.PASSWORD_LOW) {
            validator = PatternPasswordValidator.lowLevelValidator();
        } else if (type == ValidatorType.PASSWORD_HIGH) {
            validator = PatternPasswordValidator.highLevelValidator();
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
        validateAllowedValidatorTypes(type);
        final Validator validator = cache.get(type);
        return validator == null ? initValidator(type) : validator;
    }

    public void validateAllowedValidatorTypes(final ValidatorType type) {
        if (type == null) {
            throw new IllegalArgumentException("ValidatorType cannot be null");
        }
    }

}