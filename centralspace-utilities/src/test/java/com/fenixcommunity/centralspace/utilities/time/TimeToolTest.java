package com.fenixcommunity.centralspace.utilities.time;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Test;

class TimeToolTest {

    @Test
    void zoneDaylightChangeTest() {
        LocalDateTime localDateTimeBeforeDST = LocalDateTime
                .of(2018, 3, 25, 1, 55);

        assertThat(localDateTimeBeforeDST.toString())
                .isEqualTo("2018-03-25T01:55");

        ZoneId italianZoneId = ZoneId.of("Europe/Rome");
        ZonedDateTime zonedDateTimeBeforeDST = localDateTimeBeforeDST
                .atZone(italianZoneId);

        assertThat(zonedDateTimeBeforeDST.toString())
                .isEqualTo("2018-03-25T01:55+01:00[Europe/Rome]");

        ZonedDateTime zonedDateTimeAfterDST = zonedDateTimeBeforeDST
                .plus(10, ChronoUnit.MINUTES);

        assertThat(zonedDateTimeAfterDST.toString())
                .isEqualTo("2018-03-25T03:05+02:00[Europe/Rome]");

        Long deltaBetweenDatesInMinutes = ChronoUnit.MINUTES
                .between(zonedDateTimeBeforeDST,zonedDateTimeAfterDST);

        assertThat(deltaBetweenDatesInMinutes)
                .isEqualTo(10);
    }

}