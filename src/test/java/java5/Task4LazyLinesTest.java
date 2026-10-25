package java5;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;

class Task4LazyLinesTest {
    @Test
    void testFilesLinesCount(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("lazy.txt");
        Files.writeString(file, "A\nB\nC\nD");
        
        try (Stream<String> lines = Files.lines(file)) {
            assertEquals(4, lines.count(), "Должно быть 4 строки");
        }
    }
}