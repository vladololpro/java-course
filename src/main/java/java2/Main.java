package java2;

import java2.errors.*;

/* Общая точка входа для заданий Java 2. */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== Задание 1: Stack trace ===");
        StackTraceDemo demo1 = new StackTraceDemo();
        demo1.run();
        System.out.println();

        System.out.println("=== Задание 2: Checked или unchecked ===");
        ExceptionClassifier demo2 = new ExceptionClassifier();
        demo2.run();

        System.out.println("=== Задание 3: Собственное исключение ===");
        OwnExceptionDemo demo3 = new OwnExceptionDemo();
        demo3.run();
        System.out.println();

        System.out.println("=== Задание 4: try-catch-finally ===");
        TryCatchFinallyDemo demo4 = new TryCatchFinallyDemo();
        demo4.run();
        System.out.println();

        System.out.println("=== Задание 5: Try-with-resources ===");
        AutoCloseableDemo demo5 = new AutoCloseableDemo();
        demo5.run();
        System.out.println();

        System.out.println("=== Задание 6: Граница перевода исключения ===");
        ExceptionTranslationDemo demo6 = new ExceptionTranslationDemo();
        demo6.run();
        System.out.println();

        System.out.println("=== Задание 7: Ошибка как значение ===");
        ResultDemo demo7 = new ResultDemo();
        demo7.run();
    }
}