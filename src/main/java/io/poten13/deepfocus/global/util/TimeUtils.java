package io.poten13.deepfocus.global.util;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class TimeUtils {

    public static ZoneId getSeoulZoneId() {
        return ZoneId.of("Asia/Seoul");
    }

    public static LocalDate convertLocalDateFrom(long unixTimestamp) {
        return Instant.ofEpochSecond(unixTimestamp).atZone(getSeoulZoneId()).toLocalDate();
    }

    public static long getCurrentUnixTimeStamp() {
        return System.currentTimeMillis() / 1000L;
    }

    public static long addMinutesToUnixTimeStamp(long timestamp, long minutes) {
        return Instant.ofEpochSecond(timestamp)
                .plus(Duration.ofMinutes(minutes))
                .getEpochSecond();
    }
}
