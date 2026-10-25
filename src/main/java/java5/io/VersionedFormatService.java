package java5.io;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.zip.CRC32;

/* Демонстрация версионируемого формата с checksum и миграцией. */
public class VersionedFormatService {

    private static final int MAGIC = 0xBEEF;
    private static final int CURRENT_VERSION = 2;

    public void run() {
        String filename = "catalog_dir/versioned_data.bin";

        System.out.println("Запись файла версии " + CURRENT_VERSION + "...");
        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(filename))) {
            String payload = "Course V2 Data";
            byte[] payloadBytes = payload.getBytes(StandardCharsets.UTF_8);

            CRC32 crc = new CRC32();
            crc.update(payloadBytes);
            long checksum = crc.getValue();

            out.writeInt(MAGIC);
            out.writeInt(CURRENT_VERSION);
            out.writeLong(checksum);
            out.writeInt(payloadBytes.length);
            out.write(payloadBytes);
            System.out.println("Файл записан. Checksum: " + checksum);
        } catch (IOException e) {
            System.out.println("Ошибка записи: " + e.getMessage());
            return;
        }

        System.out.println("\nЧтение файла...");
        try (DataInputStream in = new DataInputStream(new FileInputStream(filename))) {
            int magic = in.readInt();
            if (magic != MAGIC) {
                System.out.println("Ошибка: неверный magic header.");
                return;
            }

            int version = in.readInt();
            if (version > CURRENT_VERSION) {
                System.out.println("Ошибка: неизвестная будущая версия " + version + ". Отказ.");
                return;
            }

            long expectedChecksum = in.readLong();
            int length = in.readInt();
            byte[] data = new byte[length];
            in.readFully(data);

            CRC32 crc = new CRC32();
            crc.update(data);
            long actualChecksum = crc.getValue();

            if (expectedChecksum != actualChecksum) {
                System.out.println("Ошибка: данные повреждены! Checksum не совпадает.");
                return;
            }

            if (version == 1) {
                System.out.println("Миграция из версии 1 в 2...");
            }

            String payload = new String(data, StandardCharsets.UTF_8);
            System.out.println("Успешно прочитано (версия " + version + "): " + payload);

        } catch (IOException e) {
            System.out.println("Ошибка чтения: " + e.getMessage());
        }
    }
}