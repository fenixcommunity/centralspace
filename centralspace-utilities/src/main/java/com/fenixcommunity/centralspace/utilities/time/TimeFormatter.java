package com.fenixcommunity.centralspace.utilities.time;

import static lombok.AccessLevel.PRIVATE;
import static org.apache.logging.log4j.util.Strings.isNotEmpty;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import lombok.experimental.FieldDefaults;

@FieldDefaults(level = PRIVATE, makeFinal = true)
public class TimeFormatter {
    //    DATE_TIME
    public static final DateTimeFormatter DT_FORMATTER_ISO = DateTimeFormatter.ISO_DATE_TIME;
    public static final DateTimeFormatter DT_FORMATTER_1 = DateTimeFormatter.ofPattern("MM/dd/yyyy - HH:mm:ss");

    //    ZONE_DATE_TIME
    public static final DateTimeFormatter ZONE_FORMATTER_ISO = DateTimeFormatter.ISO_ZONED_DATE_TIME;
    public static final DateTimeFormatter ZONE_FORMATTER_1 = DateTimeFormatter.ofPattern("MM/dd/yyyy - HH:mm:ss z");
    public static final DateTimeFormatter ZONE_FORMATTER_2 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXXZ");

    public static ZonedDateTime toZonedDateTime(String val, DateTimeFormatter formatter) {
        if (isNotEmpty(val)) {
            return ZonedDateTime.parse(val, formatter);
        } else return null;
    }

    public static ZonedDateTime toIsoZonedDateTime(String val) {
       return toZonedDateTime(val, ZONE_FORMATTER_ISO);
    }

}