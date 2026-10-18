package java4;

import java4.strings.StringIdentityDemo;
import java4.strings.ReportBuilder;

/* Общая точка входа для всех заданий Java 4. */
public class Main {
    public static void main(String[] args) {
        System.out.println("--- Задание 1: String identity ---");
        StringIdentityDemo demo1 = new StringIdentityDemo();
        demo1.run();

        System.out.println("\n--- Задание 2: Сборка строки ---");
        ReportBuilder demo2 = new ReportBuilder();
        demo2.run();
    }
}