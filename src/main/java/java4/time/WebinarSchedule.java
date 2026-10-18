package java4.time;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/* Класс для демонстрации разницы между Period и Duration. */
public class WebinarSchedule {

    public void run() {
        System.out.println("=== 1. Period: 1 календарный день ===");
        LocalDateTime before = LocalDateTime.of(2024, 3, 9, 12, 0);
        LocalDateTime after = before.plus(Period.ofDays(1));
        System.out.println("Было:  " + before);
        System.out.println("Стало: " + after);

        System.out.println("\n=== 2. Duration: 24 часа ===");
        LocalDateTime durationAfter = before.plus(Duration.ofHours(24));
        System.out.println("Было:  " + before);
        System.out.println("Стало: " + durationAfter);
        System.out.println("Period и Duration дали одинаковый результат (наивно).");

        System.out.println("\n=== 3. Ловушка перехода на летнее время (DST) ===");
        ZoneId newYork = ZoneId.of("America/New_York");
        ZonedDateTime zonedBefore = ZonedDateTime.of(before, newYork);
        
        ZonedDateTime plusPeriod = zonedBefore.plus(Period.ofDays(1));
        ZonedDateTime plusDuration = zonedBefore.plus(Duration.ofHours(24));

        System.out.println("Начало: " + zonedBefore);
        System.out.println("После + 1 день (Period):    " + plusPeriod);
        System.out.println("После + 24 часа (Duration): " + plusDuration);
        System.out.println("Разница во времени суток:   " + Duration.between(plusPeriod, plusDuration));

        System.out.println("\n=== 4. Рекомендации ===");
        System.out.println("Срок подписки (календарный месяц) -> Period");
        System.out.println("Сетевой таймаут (точные секунды) -> Duration");
    }
}