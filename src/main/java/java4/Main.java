package java4;

import java4.strings.StringIdentityDemo;
import java4.strings.ReportBuilder;
import java4.strings.CourseCode;
import java4.numbers.IntegerPolicy;
import java4.numbers.DecimalDemo;
import java4.numbers.DiscountAllocator;
import java4.numbers.RandomSampleService;
import java4.time.StudyCalendar;
import java4.time.WebinarSchedule;
import java4.time.DateParser;

/* Общая точка входа для всех заданий Java 4. */
public class Main {
    public static void main(String[] args) {
        System.out.println("--- Задание 1: String identity ---");
        StringIdentityDemo demo1 = new StringIdentityDemo();
        demo1.run();

        System.out.println("\n--- Задание 2: Сборка строки ---");
        ReportBuilder demo2 = new ReportBuilder();
        demo2.run();

        System.out.println("\n--- Задание 3: Выражение с группами ---");
        CourseCode demo3 = new CourseCode();
        demo3.run();

        System.out.println("\n--- Задание 4: Autoboxing и null ---");
        IntegerPolicy demo4 = new IntegerPolicy();
        demo4.run();

        System.out.println("\n--- Задание 5: BigDecimal ---");
        DecimalDemo demo5 = new DecimalDemo();
        demo5.run();

        System.out.println("\n--- Задание 6: Масштаб и округление ---");
        DiscountAllocator demo6 = new DiscountAllocator();
        demo6.run();

        System.out.println("\n--- Задание 7: Случайность и Math ---");
        RandomSampleService demo7 = new RandomSampleService();
        demo7.run();

        System.out.println("\n--- Задание 8: LocalDate ---");
        StudyCalendar demo8 = new StudyCalendar();
        demo8.run();

        System.out.println("\n--- Задание 9: Period и Duration ---");
        WebinarSchedule demo9 = new WebinarSchedule();
        demo9.run();

        System.out.println("\n--- Задание 10: Строгий DateTimeFormatter ---");
        DateParser demo10 = new DateParser();
        demo10.run();
    }
}