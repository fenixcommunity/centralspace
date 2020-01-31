package com.fenixcommunity.centralspace.domain.converter;

import static lombok.AccessLevel.PRIVATE;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import lombok.experimental.FieldDefaults;

@Converter(autoApply = true)
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ZonedDateTimeJpaConverter implements AttributeConverter<ZonedDateTime, Timestamp> {
    // Store always in UTC
    // Read from database (stored in UTC) and return with the system default.
    private final static ZoneId utc_zone_id = ZoneId.of("UTC");
    private final static ZoneId default_zone_id = ZoneId.systemDefault();

    @Override
    public Timestamp convertToDatabaseColumn(final ZonedDateTime zonedDateTime) {
        return (zonedDateTime == null ? null :
                Timestamp.valueOf(toUtcZoneId(zonedDateTime).toLocalDateTime()));
    }

    @Override
    public ZonedDateTime convertToEntityAttribute(final Timestamp sqlTimestamp) {
        return (sqlTimestamp == null ? null :
                toDefaultZoneId(sqlTimestamp.toLocalDateTime().atZone(utc_zone_id)));
    }

    private ZonedDateTime toUtcZoneId(final ZonedDateTime zonedDateTime) {
        return zonedDateTime.withZoneSameInstant(utc_zone_id);
    }

    private ZonedDateTime toDefaultZoneId(final ZonedDateTime zonedDateTime) {
        return zonedDateTime.withZoneSameInstant(default_zone_id);
    }

}
