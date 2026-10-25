package java5.io;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/* Демонстрация безопасной замены файла через временный файл. */
public class SafeFileReplaceService {

    public void run() {
        Path targetFile = Paths.get("catalog_dir/target_catalog.txt");
        Path tempFile = Paths.get("catalog_dir/temp_catalog.tmp");

        String oldContent = "Старый каталог\nКурс А";
        String newContent = "Новый каталог\nКурс X\nКурс Y\nКурс Z";

        try {
            Files.writeString(targetFile, oldContent);
            System.out.println("Создан целевой файл (старая версия).");
        } catch (IOException e) {
            System.out.println("Ошибка: " + e.getMessage());
            return;
        }

        System.out.println("Запись новых данных во временный файл: " + tempFile.getFileName());
        try (BufferedWriter writer = Files.newBufferedWriter(tempFile)) {
            writer.write(newContent);
        } catch (IOException e) {
            System.out.println("Ошибка записи во временный файл: " + e.getMessage());
            return;
        }

        System.out.println("Замена целевого файла...");
        try {
            Files.move(tempFile, targetFile, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
            System.out.println("Атомарное перемещение выполнено успешно.");
        } catch (AtomicMoveNotSupportedException e) {
            System.out.println("Файловая система не поддерживает atomic move. Используем обычное перемещение.");
            try {
                Files.move(tempFile, targetFile, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Обычное перемещение выполнено.");
            } catch (IOException ex) {
                System.out.println("Ошибка перемещения: " + ex.getMessage());
            }
        } catch (IOException e) {
            System.out.println("Ошибка ввода-вывода при перемещении: " + e.getMessage());
        }

        try {
            String result = Files.readString(targetFile);
            System.out.println("\nСодержимое целевого файла после замены:\n" + result);
        } catch (IOException e) {
            System.out.println("Ошибка чтения: " + e.getMessage());
        }
    }
}