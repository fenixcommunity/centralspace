package com.fenixcommunity.centralspace.utilities.time;

import static com.fenixcommunity.centralspace.utilities.validator.ValidatorType.NOT_NULL;
import static lombok.AccessLevel.PRIVATE;

import java.time.ZonedDateTime;
import java.util.Date;

import com.fenixcommunity.centralspace.utilities.validator.Validator;
import com.fenixcommunity.centralspace.utilities.validator.ValidatorFactory;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    public Date TO_OLD_DATE(final ZonedDateTime dateTime) {
        return Date.from(dateTime.toInstant());
    }
}
