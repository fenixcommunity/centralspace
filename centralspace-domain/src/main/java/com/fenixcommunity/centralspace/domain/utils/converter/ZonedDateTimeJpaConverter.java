package com.fenixcommunity.centralspace.domain.utils.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Converter(autoApply = true)
public class ZonedDateTimeJpaConverter implements AttributeConverter<ZonedDateTime, Timestamp> {
    // Store always in UTC
    // Read from database (stored in UTC) and return with the system default.
    private static ZoneId utc_zone_id = ZoneId.of("UTC");
    private static ZoneId default_zone_id = ZoneId.systemDefault();

    @Override
    public Timestamp convertToDatabaseColumn(ZonedDateTime zonedDateTime) {
        return (zonedDateTime == null ? null :
                Timestamp.valueOf(toUtcZoneId(zonedDateTime).toLocalDateTime()));
    }

    @Override
    public ZonedDateTime convertToEntityAttribute(Timestamp sqlTimestamp) {
        return (sqlTimestamp == null ? null :
                toDefaultZoneId(sqlTimestamp.toLocalDateTime().atZone(utc_zone_id)));
    }

    private ZonedDateTime toUtcZoneId(ZonedDateTime zonedDateTime) {
        return zonedDateTime.withZoneSameInstant(utc_zone_id);
    }

    private ZonedDateTime toDefaultZoneId(ZonedDateTime zonedDateTime) {
        return zonedDateTime.withZoneSameInstant(default_zone_id);
    }

}
