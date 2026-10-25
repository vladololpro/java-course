package java5.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/* Сервис для сохранения и чтения каталога курсов. */
public class CatalogFileService {

    public void run() {
        Path validPath = Paths.get("catalog.txt");
        Path directoryPath = Paths.get("catalog_dir");
        Path missingPath = Paths.get("missing.txt");

        String content = "Курс 1: Java\nКурс 2: Spring\nКурс 3: Databases";

        System.out.println("1. Запись в файл");
        try {
            Files.writeString(validPath, content);
            System.out.println("Успешно записано в " + validPath.toAbsolutePath());
        } catch (IOException e) {
            System.out.println("Ошибка записи: " + e.getMessage());
        }

        System.out.println("\n2. Чтение из файла");
        try {
            String read = Files.readString(validPath);
            System.out.println("Прочитано:\n" + read);
        } catch (IOException e) {
            System.out.println("Ошибка чтения: " + e.getMessage());
        }

        System.out.println("\n3. Чтение несуществующего файла");
        try {
            Files.readString(missingPath);
            System.out.println("Ложный успех: файл прочитан!");
        } catch (IOException e) {
            System.out.println("Ожидаемая ошибка: " + e.getClass().getSimpleName());
        }

        System.out.println("\n4. Чтение директории как файла");
        try {
            if (!Files.exists(directoryPath)) {
                Files.createDirectory(directoryPath);
            }
            Files.readString(directoryPath);
            System.out.println("Ложный успех: директория прочитана!");
        } catch (IOException e) {
            System.out.println("Ожидаемая ошибка: " + e.getClass().getSimpleName());
        }
    }
}