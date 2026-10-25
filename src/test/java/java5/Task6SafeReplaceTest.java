package java5;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.IOException;
import java.nio.file.*;
import static org.junit.jupiter.api.Assertions.*;

class Task6SafeReplaceTest {
    @Test
    void testSafeReplace(@TempDir Path tempDir) throws IOException {
        Path target = tempDir.resolve("target.txt");
        Path temp = tempDir.resolve("temp.tmp");
        
        Files.writeString(target, "Old");
        Files.writeString(temp, "New");
        
        // Замена
        Files.move(temp, target, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
        
        assertEquals("New", Files.readString(target));
        assertFalse(Files.exists(temp), "Временный файл должен исчезнуть");
    }
}