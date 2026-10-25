package java5.io;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/* Демонстрация бинарного формата с magic header и версией. */
public class BinaryFormatService {

    private static final int MAGIC = 0xCAFE; // Магическое число (наш "отпечаток")
    private static final int VERSION = 1;    // Текущая версия формата

    public void run() {
        String filename = "catalog_dir/binary_data.bin";

        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(filename))) {
            out.writeInt(MAGIC);
            out.writeInt(VERSION);
            out.writeInt(3);
            out.writeUTF("Java Course");
            out.writeDouble(199.99);
            out.writeUTF("Spring Course");
            out.writeDouble(299.99);
            out.writeUTF("SQL Course");
            out.writeDouble(149.99);
            System.out.println("Файл успешно записан: " + filename);
        } catch (IOException e) {
            System.out.println("Ошибка записи: " + e.getMessage());
        }

        System.out.println("\nЧтение файла:");
        try (DataInputStream in = new DataInputStream(new FileInputStream(filename))) {
            int magic = in.readInt();
            if (magic != MAGIC) {
                System.out.println("Ошибка: это не наш файл (неверный magic header)");
                return;
            }

            int version = in.readInt();
            if (version != VERSION) {
                System.out.println("Ошибка: неизвестная версия " + version);
                return;
            }

            int count = in.readInt();
            System.out.println("Найдено записей: " + count);

            for (int i = 0; i < count; i++) {
                String name = in.readUTF();
                double price = in.readDouble();
                System.out.println(" - " + name + ": " + price);
            }

        } catch (EOFException e) {
            System.out.println("Ошибка: файл обрезан или поврежден (EOF)");
        } catch (IOException e) {
            System.out.println("Ошибка чтения: " + e.getMessage());
        }
    }
}