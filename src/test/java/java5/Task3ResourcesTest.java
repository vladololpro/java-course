package java5;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;

class Task3ResourcesTest {
    @Test
    void testResourcesClosedOnError(@TempDir Path tempDir) throws IOException {
        Path input = tempDir.resolve("in.txt");
        Path output = tempDir.resolve("out.txt");
        Files.writeString(input, "Line 1\nLine 2");

        try (BufferedReader r = Files.newBufferedReader(input);
             BufferedWriter w = Files.newBufferedWriter(output)) {
            w.write(r.readLine());
            throw new RuntimeException("Ошибка");
        } catch (RuntimeException ignored) {}

        assertEquals("Line 1", Files.readString(output), "Данные должны записаться до ошибки");
    }
}