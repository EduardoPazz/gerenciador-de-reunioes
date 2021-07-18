import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class LocalDateTimeTest {

    @Test
    public void testaLocalDateTime() {
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("u")));
    }
}
