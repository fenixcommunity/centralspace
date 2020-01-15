package com.fenixcommunity.centralspace.utilities.time;

import com.fenixcommunity.centralspace.utilities.validator.Validator;
import com.fenixcommunity.centralspace.utilities.validator.ValidatorFactory;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.ZonedDateTime;

import static com.fenixcommunity.centralspace.utilities.validator.ValidatorType.NOT_NULL;
import static lombok.AccessLevel.PRIVATE;

@Log4j2
@Component
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class TimeTool {

    private final Validator validator;

    @Autowired
    public TimeTool(final ValidatorFactory validatorFactory) {
        this.validator = validatorFactory.getInstance(NOT_NULL);
    }

    public boolean IS_EQUAL(final ZonedDateTime dateFirst, final ZonedDateTime dateSecond) {
        return validator.isValidAll(dateFirst, dateSecond) && dateFirst.isEqual(dateSecond);
    }

    public java.util.Date TO_DATE(final ZonedDateTime dateTime) {
        return Date.from(dateTime.toInstant());
    }
}
