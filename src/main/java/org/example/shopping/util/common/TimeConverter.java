package org.example.shopping.util.common;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class TimeConverter {

    // 날짜 관련해서는 BDMS 를 이용하여 지금은 사용하지 않음.
    // Date 사용 지양. LocalDateTime 이나 LocalDate 사용 지향.
    public static String toDayToString() {
        LocalDateTime date = LocalDateTime.now(ZoneId.of("Asia/Seoul"));

        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public static Timestamp localDateTimeToTimeStamp(LocalDateTime ldt) {
        return Timestamp.valueOf(ldt);
    }

    public static LocalDateTime timeStampToLocalDateTime(Timestamp ts) {
        return ts.toLocalDateTime();
    }
}
