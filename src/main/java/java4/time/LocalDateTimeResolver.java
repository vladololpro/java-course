package java4.time;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/* Класс для демонстрации хранения времени в Instant и отображения в разных зонах. */
public class LocalDateTimeResolver {

    public void run() {
        System.out.println("=== 1. Хранение момента времени как Instant ===");
        Instant webinarMoment = Instant.parse("2024-10-15T14:00:00Z");
        System.out.println("Хранится в базе (UTC): " + webinarMoment);

        System.out.println("\n=== 2. Отображение в трех зонах ===");
        ZoneId zoneMoscow = ZoneId.of("Europe/Moscow");
        ZoneId zoneNewYork = ZoneId.of("America/New_York");
        ZoneId zoneTokyo = ZoneId.of("Asia/Tokyo");

        System.out.println("Москва:    " + webinarMoment.atZone(zoneMoscow));
        System.out.println("Нью-Йорк:  " + webinarMoment.atZone(zoneNewYork));
        System.out.println("Токио:     " + webinarMoment.atZone(zoneTokyo));

        System.out.println("\n=== 3. Опасность серверной зоны ===");
        ZoneId serverDefault = ZoneId.systemDefault();
        System.out.println("Текущая системная зона сервера: " + serverDefault);

        ZonedDateTime inServerZone = webinarMoment.atZone(serverDefault);
        System.out.println("Если использовать серверную зону: " + inServerZone);
        
        System.out.println("\nБизнес-результат не должен зависеть от того,");
        System.out.println("где физически стоит сервер. Храним в UTC, показываем в зоне пользователя.");
    }
}