package time;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;

/**
 * @author markrenChina
 */
public class ComputeTime {

    public static void main(String[] args) throws InterruptedException {
        Instant start = Instant.now();
        System.out.println(start);
        Instant end = start.plusSeconds(275);
        System.out.println(end);
        Duration timeElapsed = Duration.between(start, end);
        System.out.printf("%d:%d\n", timeElapsed.toMinutesPart(), timeElapsed.toSecondsPart());
        System.out.printf("%tM:%tS\n", timeElapsed.toMillis(), timeElapsed.toMillis());
        //获取所有时区
        ZoneId.getAvailableZoneIds().forEach(System.out::println);

    }
}
