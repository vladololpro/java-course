package java5;

import java5.io.CatalogFileService;
import java5.io.BufferedTextService;

/* Общая точка входа для заданий Java 5. */
public class Main {
    public static void main(String[] args) {
        System.out.println("--- Задание 1: Files.readString/writeString ---");
        CatalogFileService demo1 = new CatalogFileService();
        demo1.run();

        System.out.println("\n--- Задание 2: Reader/Writer ---");
        BufferedTextService demo2 = new BufferedTextService();
        demo2.run();
    }
}