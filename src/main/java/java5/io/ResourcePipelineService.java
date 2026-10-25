package java5.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/* Демонстрация порядка закрытия ресурсов и подавления исключений. */
public class ResourcePipelineService {

    public void run() {
        String inputFile = "pipeline_in.txt";
        String outputFile = "pipeline_out.txt";

        try (BufferedWriter initWriter = new BufferedWriter(new FileWriter(inputFile))) {
            initWriter.write("Строка 1\n");
            initWriter.write("Строка 2\n");
            initWriter.write("Строка 3\n");
        } catch (IOException e) {
            System.out.println("Ошибка создания файла: " + e.getMessage());
            return;
        }

        System.out.println("Начинаем чтение и запись (с искусственным исключением)...");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            
            String line;
            int lineCount = 0;
            
            while ((line = reader.readLine()) != null) {
                lineCount++;
                writer.write("Скопировано: " + line + "\n");
                
                if (lineCount == 2) {
                    throw new RuntimeException("Искусственная ошибка посреди пайплайна!");
                }
            }
            
        } catch (IOException e) {
            System.out.println("Ошибка ввода-вывода: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("Поймано исключение: " + e.getMessage());
        }

        System.out.println("Программа продолжает работу. Оба ресурса были закрыты.");
    }
}