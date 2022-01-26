package time;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 */
public class TimeUtils {

    private static Long basicsTime = null;

    public static Long getBasicsTime() {
        return System.currentTimeMillis();
    }

    public static LocalDateTime getBasicsLocalDateTime() {
        return long2LocalDateTime(getBasicsTime());
    }

    public static ZonedDateTime getBasicsZonedDateTime() {
        return long2ZonedDateTime(getBasicsTime());
    }

    private static ZonedDateTime long2ZonedDateTime(Long time) {
        return ZonedDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault());
    }

    public static LocalDateTime long2LocalDateTime(long time) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault());
    }

    public static void main(String[] args) {
        DateTimeFormatter ID_END_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        System.out.println(ID_END_FORMAT.format(TimeUtils.getBasicsLocalDateTime()));
        System.out.println(ID_END_FORMAT.format(TimeUtils.getBasicsZonedDateTime()));
        LocalDateTime time = LocalDateTime.ofInstant(Instant.ofEpochMilli(getBasicsTime()), ZoneId.systemDefault());
        System.out.println(time);
        var second = time.atZone(ZoneId.systemDefault()).toEpochSecond();
        System.out.println(second);
        System.out.println(LocalDateTime.ofInstant(Instant.ofEpochSecond(second), ZoneId.systemDefault()));
    }
}
