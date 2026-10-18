package java4;

import org.junit.jupiter.api.Test;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import static org.junit.jupiter.api.Assertions.*;

class TimeTest {

    @Test
    void testLocalDateImmutability() {
        LocalDate date = LocalDate.of(2024, 9, 2);
        LocalDate nextWeek = date.plusWeeks(1);
        
        assertEquals(LocalDate.of(2024, 9, 2), date, "Исходная дата не меняется");
        assertEquals(LocalDate.of(2024, 9, 9), nextWeek);
    }

    @Test
    void testLeapYear() {
        assertTrue(LocalDate.of(2024, 1, 1).isLeapYear());
        assertFalse(LocalDate.of(2023, 1, 1).isLeapYear());
        assertTrue(LocalDate.of(2000, 1, 1).isLeapYear());
        assertFalse(LocalDate.of(1900, 1, 1).isLeapYear());
    }

    @Test
    void testInstantZones() {
        Instant instant = Instant.parse("2024-10-15T14:00:00Z");
        ZonedDateTime moscow = instant.atZone(ZoneId.of("Europe/Moscow"));
        
        assertEquals(17, moscow.getHour(), "В Москве UTC+3");
    }

    @Test
    void testStrictParsing() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.uuuu")
            .withResolverStyle(ResolverStyle.STRICT);
        
        assertDoesNotThrow(() -> LocalDate.parse("15.10.2024", formatter));
        
        assertThrows(DateTimeParseException.class, () -> LocalDate.parse("31.02.2024", formatter));
    }

    @Test
    void testPeriodVsDuration() {
        LocalDateTime start = LocalDateTime.of(2024, 3, 9, 12, 0);
        assertEquals(start.plus(Period.ofDays(1)), start.plus(Duration.ofHours(24)));
    }
}