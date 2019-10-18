package com.fenixcommunity.centralspace.validator;

import com.fenixcommunity.centralspace.common.Level;
import org.springframework.util.Assert;

@Component
public class ValidatorFactory {
    //TODO rozszerz o DataValidations w TLK
    // https://www.baeldung.com/spring-assert

    public static Validator getValidator(ValidatorType type, Level level) {
        Assert.notNull(type);
        if(type == ValidatorType.PASSWORD) {
            return getPasswordValidatorInstance();
        }
        Component? Singleton? return DataValidations.getInstance();
    }

    private static PasswordValidator getPasswordValidatorInstance() {

    }

}
