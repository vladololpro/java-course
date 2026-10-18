package java4;

import java4.strings.StringIdentityDemo;
import java4.strings.ReportBuilder;
import java4.strings.CourseCode;
import java4.numbers.IntegerPolicy;
import java4.numbers.DecimalDemo;

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
    }
}