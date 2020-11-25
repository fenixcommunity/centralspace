package com.fenixcommunity.centralspace.utilities.time;

import static com.fenixcommunity.centralspace.utilities.validator.ValidatorType.NOT_NULL;
import static lombok.AccessLevel.PRIVATE;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import com.fenixcommunity.centralspace.utilities.validator.Validator;
import com.fenixcommunity.centralspace.utilities.validator.ValidatorFactory;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.validator.GenericValidator;
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

    //annotations @Past @Future
    public boolean isDate(String value, String pattern) {
        return GenericValidator.isDate(value, pattern, true);
    }

    public boolean isEqual(final ZonedDateTime dateFirst, final ZonedDateTime dateSecond) {
        return validator.isValidAll(dateFirst, dateSecond) && dateFirst.isEqual(dateSecond);
    }

    public ZonedDateTime toZonedDateTime(String val, DateTimeFormatter formatter) {
       return TimeFormatter.toZonedDateTime(val, formatter);
    }

    public Date toOldDate(final ZonedDateTime dateTime) {
        return Date.from(dateTime.toInstant());
    }

    public ZonedDateTime fromMilliseconds(long timeInMilliseconds) {
        return ZonedDateTime.ofInstant(Instant.ofEpochMilli(timeInMilliseconds), ZoneOffset.UTC);
    }

    public long toMilliseconds(ZonedDateTime zonedDateTime) {
        return zonedDateTime.toInstant().toEpochMilli();
    }

    public ZonedDateTime fromSeconds(long timeInSeconds) {
        return ZonedDateTime.ofInstant(Instant.ofEpochSecond(timeInSeconds), ZoneOffset.UTC);
    }
}
