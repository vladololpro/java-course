package java5.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

/* Демонстрация Files.lines и владения ресурсом. */
public class LazyLinesService {

    public void run() {
        Path file = Paths.get("catalog_dir/lazy_lines.txt");

        try {
            Files.writeString(file, "Строка А\nСтрока Б\nСтрока В\n");
        } catch (IOException e) {
            System.out.println("Ошибка создания файла: " + e.getMessage());
            return;
        }

        System.out.println("Правильный вариант (внутри try-with-resources):");
        try (Stream<String> lines = Files.lines(file)) {
            long count = lines.filter(line -> line.startsWith("Строка"))
                              .count();
            System.out.println("Найдено строк: " + count);
        } catch (IOException e) {
            System.out.println("Ошибка чтения: " + e.getMessage());
        }

        System.out.println("\nОшибочный вариант (возврат наружу):");
        try {
            Stream<String> leakedStream = getLeakedStream(file);
            System.out.println("Поток получен. Но кто его закроет?");
            leakedStream.close(); 
            System.out.println("Поток закрыт вручную.");
        } catch (IOException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private Stream<String> getLeakedStream(Path path) throws IOException {
        return Files.lines(path); 
    }
}