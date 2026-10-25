package java5;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;

class Task5BinaryTest {
    @Test
    void testMagicAndVersion(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("data.bin");
        try (DataOutputStream out = new DataOutputStream(Files.newOutputStream(file))) {
            out.writeInt(0xCAFE);
            out.writeInt(1);
        }
        try (DataInputStream in = new DataInputStream(Files.newInputStream(file))) {
            assertEquals(0xCAFE, in.readInt());
            assertEquals(1, in.readInt());
        }
    }
    @Test
    void testTruncatedFile(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("trunc.bin");
        try (DataOutputStream out = new DataOutputStream(Files.newOutputStream(file))) {
            out.writeInt(0xCAFE); // Только magic
        }
        try (DataInputStream in = new DataInputStream(Files.newInputStream(file))) {
            in.readInt();
            assertThrows(EOFException.class, in::readInt);
        }
    }
}