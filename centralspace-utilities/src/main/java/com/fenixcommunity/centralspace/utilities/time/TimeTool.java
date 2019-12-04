package com.fenixcommunity.centralspace.utilities.time;

import com.fenixcommunity.centralspace.utilities.validator.Validator;
import com.fenixcommunity.centralspace.utilities.validator.ValidatorFactory;
import com.fenixcommunity.centralspace.utilities.validator.ValidatorType;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Log4j2
@Component
public class TimeTool {

    private ValidatorFactory validatorFactory;

    @Autowired
    public TimeTool(ValidatorFactory validatorFactory) {
        this.validatorFactory = validatorFactory;
    }

    public boolean IS_EQUAL(ZonedDateTime dateFirst, ZonedDateTime dateSecond) {
        Validator validator = validatorFactory.getInstance(ValidatorType.NOT_NULL);
        return validator.isValidAll(dateFirst, dateSecond) && dateFirst.isEqual(dateSecond);
    }
}
