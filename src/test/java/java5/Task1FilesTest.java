package java5;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;

class Task1FilesTest {
    @Test
    void testReadWriteString(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("test.txt");
        String content = "Курс 1\nКурс 2";
        Files.writeString(file, content);
        assertEquals(content, Files.readString(file));
    }
    @Test
    void testReadMissingFile(@TempDir Path tempDir) {
        Path missingFile = tempDir.resolve("missing.txt");
        assertThrows(IOException.class, () -> Files.readString(missingFile));
    }
}