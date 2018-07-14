package org.isegodin.expenses.adviser.telegram.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

/**
 * @author isegodin
 */
public class TimeUtil {

    public static final ZoneId DEFAULT_ZONE = ZoneId.of("Europe/Kiev");

    public static ZoneOffset zoneToOffset(ZoneId zone) {
        Instant instant = Instant.now();

        return zone.getRules().getOffset(instant);
    }

    public static OffsetDateTime thisMonthFrom() {
        LocalDateTime localDateTime = LocalDateTime.now().withDayOfMonth(1).truncatedTo(ChronoUnit.DAYS);

        return OffsetDateTime.of(localDateTime, zoneToOffset(DEFAULT_ZONE));
    }

    public static OffsetDateTime thisMonthTo() {
        LocalDateTime localDateTime = LocalDateTime.now().with(TemporalAdjusters.lastDayOfMonth()).with(LocalTime.MAX);

        return OffsetDateTime.of(localDateTime, zoneToOffset(DEFAULT_ZONE));
    }

}
