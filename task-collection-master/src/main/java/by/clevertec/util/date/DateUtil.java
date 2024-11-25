package by.clevertec.util.date;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.YEARS;

public class DateUtil {

    public static int countYears(LocalDate localDate) {
        return countYearsBetween(localDate, LocalDate.now());
    }

    public static int countYearsBetween(LocalDate localDate1, LocalDate localDate2) {
        return (int) YEARS.between(localDate1, localDate2);
    }
}
