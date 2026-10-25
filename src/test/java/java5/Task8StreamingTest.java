package java5;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;

class Task8StreamingTest {
    @Test
    void testCancellation(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("big.txt");
        Files.writeString(file, "OK\nBAD1\nOK\nBAD2\nOK\nBAD3\nOK");
        
        int maxErrors = 2;
        int errorCount = 0;
        int linesRead = 0;
        
        try (BufferedReader reader = Files.newBufferedReader(file)) {
            String line;
            while ((line = reader.readLine()) != null) {
                linesRead++;
                if (!line.equals("OK")) {
                    errorCount++;
                    if (errorCount >= maxErrors) break;
                }
            }
        }
        assertEquals(4, linesRead, "Чтение останавливается на пороге ошибок");
        assertEquals(2, errorCount);
    }
}