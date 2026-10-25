package java5.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/* Демонстрация потокового импорта с отменой по порогу ошибок. */
public class StreamingImportService {

    private static final int MAX_ERRORS = 3;

    public void run() {
        Path file = Paths.get("catalog_dir/large_catalog.txt");

        try {
            StringBuilder sb = new StringBuilder();
            for (int i = 1; i <= 20; i++) {
                if (i == 5 || i == 10 || i == 15 || i == 18 || i == 20) {
                    sb.append("ERROR_LINE_").append(i).append("\n");
                } else {
                    sb.append("COURSE_").append(i).append(": OK\n");
                }
            }
            Files.writeString(file, sb.toString());
            System.out.println("Создан файл с 20 строками (5 из них с ошибками).");
        } catch (IOException e) {
            System.out.println("Ошибка создания файла: " + e.getMessage());
            return;
        }

        System.out.println("\nНачинаем потоковый импорт (порог ошибок: " + MAX_ERRORS + ")...");
        processLargeFile(file);
    }

    private void processLargeFile(Path file) {
        int totalLines = 0;
        int validLines = 0;
        List<String> errors = new ArrayList<>();
        boolean cancelled = false;

        try (BufferedReader reader = Files.newBufferedReader(file)) {
            String line;
            while ((line = reader.readLine()) != null) {
                totalLines++;

                if (!line.contains(":")) {
                    errors.add("Строка " + totalLines + ": " + line);
                    if (errors.size() >= MAX_ERRORS) {
                        cancelled = true;
                        break;
                    }
                } else {
                    validLines++;
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
            return;
        }

        System.out.println("Обработано строк (до отмены): " + totalLines);
        System.out.println("Успешных строк: " + validLines);
        System.out.println("Найдено ошибок (первые " + MAX_ERRORS + "):");
        for (String err : errors) {
            System.out.println("  - " + err);
        }

        if (cancelled) {
            System.out.println("\nИМПОРТ ОТМЕНЕН: превышен порог ошибок (" + MAX_ERRORS + ").");
        } else {
            System.out.println("\nИМПОРТ ЗАВЕРШЕН.");
        }
    }
}