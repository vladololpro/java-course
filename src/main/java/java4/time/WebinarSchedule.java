package java4.time;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/* Класс для демонстрации Period, Duration и DST-кейсов. */
public class WebinarSchedule {

    public void run() {
        System.out.println("=== 1. Period: 1 календарный день ===");
        LocalDateTime before = LocalDateTime.of(2024, 3, 9, 12, 0);
        System.out.println("Было:  " + before);
        System.out.println("Стало: " + before.plus(Period.ofDays(1)));

        System.out.println("\n=== 2. Duration: 24 часа ===");
        System.out.println("Было:  " + before);
        System.out.println("Стало: " + before.plus(Duration.ofHours(24)));

        System.out.println("\n=== 3. Ловушка перехода на летнее время ===");
        ZoneId newYork = ZoneId.of("America/New_York");
        ZonedDateTime zonedBefore = ZonedDateTime.of(before, newYork);
        ZonedDateTime plusPeriod = zonedBefore.plus(Period.ofDays(1));
        ZonedDateTime plusDuration = zonedBefore.plus(Duration.ofHours(24));
        System.out.println("Начало: " + zonedBefore);
        System.out.println("После + 1 день (Period):    " + plusPeriod);
        System.out.println("После + 24 часа (Duration): " + plusDuration);
        System.out.println("Разница: " + Duration.between(plusPeriod, plusDuration));

        System.out.println("\n=== 4. Рекомендации ===");
        System.out.println("Срок подписки (календарный месяц) -> Period");
        System.out.println("Сетевой таймаут (точные секунды) -> Duration");
    }

    public void runDstCase() {
        System.out.println("=== 1. Попытка создать несуществующее время (весна) ===");
        ZoneId newYork = ZoneId.of("America/New_York");
        LocalDateTime gapLocal = LocalDateTime.of(2024, 3, 10, 2, 30);
        
        try {
            ZonedDateTime gapZoned = createWebinar(gapLocal, newYork);
            System.out.println("Создан вебинар: " + gapZoned);
        } catch (InvalidLocalTimeException e) {
            System.out.println("ОШИБКА: " + e.getMessage());
        }

        System.out.println("\n=== 2. Добавление часа и дня вокруг перехода ===");
        ZonedDateTime beforeGap = ZonedDateTime.of(2024, 3, 10, 1, 30, 0, 0, newYork);
        System.out.println("Начало: " + beforeGap);
        System.out.println("+ 1 час: " + beforeGap.plus(Duration.ofHours(1)));
        System.out.println("+ 1 день: " + beforeGap.plus(Period.ofDays(1)));
    }

    private ZonedDateTime createWebinar(LocalDateTime local, ZoneId zone) {
        ZonedDateTime zoned = ZonedDateTime.of(local, zone);
        if (!zoned.toLocalDateTime().equals(local)) {
            throw new InvalidLocalTimeException("Время " + local + " не существует в зоне " + zone);
        }
        return zoned;
    }
}