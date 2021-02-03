package com.redhat.bz;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class JavaTimeUtil {

    private static final String CET_TIME_ZONE_ID = "CET";
    public static final ZoneId ZONE_ID = ZoneId.of(CET_TIME_ZONE_ID);

    public static Date convertLocalDateToDate(LocalDate dateToConvert) {
        return Date.from(dateToConvert.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }

    public static Date convertLocalDateTimeToDate(LocalDateTime dateToConvert) {
        return Date
                .from(dateToConvert.atZone(ZoneId.systemDefault())
                        .toInstant());
    }

}
