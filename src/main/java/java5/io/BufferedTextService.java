package java5.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/* Демонстрация работы через BufferedReader и BufferedWriter. */
public class BufferedTextService {

    public void run() {
        String filename = "buffered_text.txt";

        // Запись через BufferedWriter
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("Первая строка");
            writer.newLine();
            writer.write("Вторая строка");
            writer.newLine();
            writer.write("Третья строка");
            System.out.println("Записано 3 строки.");
        } catch (IOException e) {
            System.out.println("Ошибка записи: " + e.getMessage());
        }

        int lineCount = 0;
        int charCount = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lineCount++;
                charCount += line.length();
                System.out.println("Прочитано: " + line);
            }
        } catch (IOException e) {
            System.out.println("Ошибка чтения: " + e.getMessage());
        }

        System.out.println("\nВсего строк: " + lineCount);
        System.out.println("Всего символов: " + charCount);
    }
}