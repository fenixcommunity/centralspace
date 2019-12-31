package com.fenixcommunity.centralspace.utilities.time;

import com.fenixcommunity.centralspace.utilities.validator.Validator;
import com.fenixcommunity.centralspace.utilities.validator.ValidatorFactory;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.ZonedDateTime;

import static com.fenixcommunity.centralspace.utilities.validator.ValidatorType.NOT_NULL;

@Log4j2
@Component
public class TimeTool {

    private final Validator validator;

    @Autowired
    public TimeTool(ValidatorFactory validatorFactory) {
        this.validator = validatorFactory.getInstance(NOT_NULL);
    }

    public boolean IS_EQUAL(ZonedDateTime dateFirst, ZonedDateTime dateSecond) {
        return validator.isValidAll(dateFirst, dateSecond) && dateFirst.isEqual(dateSecond);
    }

    public java.util.Date TO_DATE(ZonedDateTime dateTime) {
        return Date.from(dateTime.toInstant());
    }
}
