package java5;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;

class Task2BufferedTest {
    @Test
    void testBufferedReadWrite(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("buffered.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file.toFile()))) {
            writer.write("Первая\nВторая\nТретья");
        }
        int lines = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(file.toFile()))) {
            while (reader.readLine() != null) lines++;
        }
        assertEquals(3, lines, "Должно быть прочитано 3 строки");
    }
}