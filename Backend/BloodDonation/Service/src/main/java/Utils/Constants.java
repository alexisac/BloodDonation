package Utils;

import java.time.LocalDateTime;

public class Constants {

    public static final Integer MIN_AGE_FOR_DONATE = 16; // years
    public static final Integer MIN_TIME_FOR_LAST_DONATION = 56; // days

    // 01.01.1900 00:00
    public static final LocalDateTime DEFAULT_DATE_TIME = LocalDateTime.of(1900, 1, 1, 0, 0);

    public static final String DATE_FORMAT = "dd.MM.yyyy HH:mm";
}
