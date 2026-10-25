package java5;

import java5.io.CatalogFileService;
import java5.io.BufferedTextService;
import java5.io.ResourcePipelineService;
import java5.io.LazyLinesService;
import java5.io.BinaryFormatService;
import java5.io.SafeFileReplaceService;
import java5.io.CatalogImporterService;

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

        System.out.println("\n--- Задание 5: Бинарный формат ---");
        BinaryFormatService demo5 = new BinaryFormatService();
        demo5.run();

        System.out.println("\n--- Задание 6: Временный файл и move ---");
        SafeFileReplaceService demo6 = new SafeFileReplaceService();
        demo6.run();

        System.out.println("\n--- Задание 7: Частично повреждённый импорт ---");
        CatalogImporterService demo7 = new CatalogImporterService();
        demo7.run();
    }
}