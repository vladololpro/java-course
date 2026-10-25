package java5;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.CRC32;
import static org.junit.jupiter.api.Assertions.*;

class Task9VersionedTest {
    @Test
    void testChecksumVerification(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("ver.bin");
        String data = "Some Data";
        byte[] bytes = data.getBytes();
        
        CRC32 crc = new CRC32();
        crc.update(bytes);
        long expectedSum = crc.getValue();

        try (DataOutputStream out = new DataOutputStream(Files.newOutputStream(file))) {
            out.writeInt(0xBEEF);
            out.writeInt(2);
            out.writeLong(expectedSum);
            out.writeInt(bytes.length);
            out.write(bytes);
        }

        try (DataInputStream in = new DataInputStream(Files.newInputStream(file))) {
            assertEquals(0xBEEF, in.readInt());
            assertEquals(2, in.readInt());
            long sum = in.readLong();
            int len = in.readInt();
            byte[] readBytes = new byte[len];
            in.readFully(readBytes);
            
            assertEquals(expectedSum, sum, "Checksum должен совпадать");
            assertArrayEquals(bytes, readBytes, "Данные должны совпадать");
        }
    }
}