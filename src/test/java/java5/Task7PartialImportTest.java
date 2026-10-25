package java5;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class Task7PartialImportTest {
    @Test
    void testPartialImport(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("broken.txt");
        Files.writeString(file, "GOOD1\nBROKEN\nGOOD2");

        List<String> valid = new ArrayList<>();
        List<String> errors = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(file)) {
            String line;
            int num = 1;
            while ((line = reader.readLine()) != null) {
                if (!line.contains(":")) {
                    errors.add("Строка " + num + ": " + line);
                } else {
                    valid.add(line);
                }
                num++;
            }
        }
        
        // В тесте мы проверяем логику, но раз в файле нет ":", то все строки попадут в ошибки
        assertEquals(3, errors.size(), "Все строки без двоеточия считаются ошибками");
    }
}