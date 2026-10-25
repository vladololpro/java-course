package java5;

import java5.io.CatalogFileService;
import java5.io.BufferedTextService;
import java5.io.ResourcePipelineService;
import java5.io.LazyLinesService;

/* Общая точка входа для заданий Java 5. */
public class Main {
    public static void main(String[] args) {
        System.out.println("--- Задание 1: Files.readString/writeString ---");
        CatalogFileService demo1 = new CatalogFileService();
        demo1.run();

        System.out.println("\n--- Задание 2: Reader/Writer ---");
        BufferedTextService demo2 = new BufferedTextService();
        demo2.run();

        System.out.println("\n--- Задание 3: Try-with-resources ---");
        ResourcePipelineService demo3 = new ResourcePipelineService();
        demo3.run();

        System.out.println("\n--- Задание 4: Ленивые строки ---");
        LazyLinesService demo4 = new LazyLinesService();
        demo4.run();
    }
}