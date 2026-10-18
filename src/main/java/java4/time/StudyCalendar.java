package java4.time;

import java.time.LocalDate;

/* Класс для демонстрации работы с LocalDate и календарными правилами. */
public class StudyCalendar {

    public void run() {

        System.out.println("=== 1. Начало модулей ===");
        LocalDate module1Start = LocalDate.of(2024, 9, 2);
        LocalDate module2Start = module1Start.plusWeeks(8);
        LocalDate module3Start = module2Start.plusWeeks(8);

        System.out.println("Модуль 1: " + module1Start);
        System.out.println("Модуль 2: " + module2Start);
        System.out.println("Модуль 3: " + module3Start);

        System.out.println("\n=== 2. Неизменяемость ===");
        LocalDate base = LocalDate.of(2024, 9, 2);
        LocalDate shifted = base.plusDays(10);
        System.out.println("Исходная дата: " + base);
        System.out.println("После plusDays(10): " + shifted);
        System.out.println("Исходная осталась прежней: " + (base.getDayOfMonth() == 2));

        System.out.println("\n=== 3. Последний учебный день ===");
        LocalDate module3End = module3Start.plusWeeks(8).minusDays(1);
        System.out.println("Начало модуля 3: " + module3Start);
        System.out.println("Конец модуля 3: " + module3End);
        System.out.println("День недели конца: " + module3End.getDayOfWeek());

        System.out.println("\n=== 4. Високосный год ===");
        LocalDate leapCheck1 = LocalDate.of(2024, 2, 29);
        LocalDate leapCheck2 = LocalDate.of(2023, 2, 28);
        System.out.println("2024 високосный: " + leapCheck1.isLeapYear() + " (29 февраля существует)");
        System.out.println("2023 високосный: " + leapCheck2.isLeapYear());
        System.out.println("2000 високосный: " + LocalDate.of(2000, 1, 1).isLeapYear());
        System.out.println("1900 високосный: " + LocalDate.of(1900, 1, 1).isLeapYear());
    }
}