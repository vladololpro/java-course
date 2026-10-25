package java5.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/* Демонстрация частичного импорта с фиксацией ошибок. */
public class CatalogImporterService {

    // Контракт результата импорта
    public static class ImportResult {
        public final List<String> validCourses = new ArrayList<>();
        public final List<String> errors = new ArrayList<>();
        
        public boolean isSuccess() {
            return errors.isEmpty();
        }
    }

    public void run() {
        Path file = Paths.get("catalog_dir/broken_catalog.txt");

        try {
            Files.writeString(file, "JAVA-101: Java Basics\n" + "SPRING-202: Spring Boot\n" + "BROKEN_LINE_NO_COLON\n" + "SQL-303: Databases\n");
            System.out.println("Создан тестовый файл с ошибкой на строке 3.");
        } catch (IOException e) {
            System.out.println("Ошибка создания файла: " + e.getMessage());
            return;
        }

        System.out.println("\nНачинаем импорт...");
        ImportResult result = importCatalog(file);

        System.out.println("\nУспешно импортировано: " + result.validCourses.size());
        for (String course : result.validCourses) {
            System.out.println("  + " + course);
        }

        System.out.println("\nОшибки (" + result.errors.size() + "):");
        for (String error : result.errors) {
            System.out.println(" - " + error);
        }

        System.out.println("\nСтатус импорта: " + (result.isSuccess() ? "УСПЕХ" : "ЧАСТИЧНЫЙ УСПЕХ"));
    }

    private ImportResult importCatalog(Path file) {
        ImportResult result = new ImportResult();

        try (BufferedReader reader = Files.newBufferedReader(file)) {
            String line;
            int lineNumber = 1;

            while ((line = reader.readLine()) != null) {
                if (!line.contains(":")) {
                    result.errors.add("Строка " + lineNumber + ": отсутствует разделитель ':' (текст: " + line + ")");
                } else {
                    result.validCourses.add(line);
                }
                lineNumber++;
            }
        } catch (IOException e) {
            result.errors.add("Ошибка чтения файла: " + e.getMessage());
        }

        return result;
    }
}