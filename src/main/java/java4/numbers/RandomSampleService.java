package java4.numbers;

import java.util.Random;
import java.util.random.RandomGenerator;

/* Класс для демонстрации генерации случайных чисел с seed. */
public class RandomSampleService {

    public void run() {
        System.out.println("=== 1. Тестовый источник (детерминированный) ===");
        RandomGenerator testSource = new Random(42);
        generateSamples(testSource, "Test (seed=42)");

        System.out.println("\n=== 2. Тестовый источник (повторный запуск) ===");
        RandomGenerator testSource2 = new Random(42);
        generateSamples(testSource2, "Test (seed=42) повторно");

        System.out.println("\n=== 3. Production источник (случайный) ===");
        RandomGenerator prodSource = new Random();
        generateSamples(prodSource, "Production (без seed)");

        System.out.println("\n=== 4. Математические функции ===");
        double val = -5.5;
        System.out.println("Math.abs(" + val + ") = " + Math.abs(val));
        System.out.println("Math.round(" + val + ") = " + Math.round(val));
        System.out.println("Math.ceil(" + val + ") = " + Math.ceil(val));
        System.out.println("Math.floor(" + val + ") = " + Math.floor(val));
    }

    private void generateSamples(RandomGenerator source, String label) {
        int min = 50;
        int max = 100;
        int count = 5;

        System.out.print(label + ": [");
        for (int i = 0; i < count; i++) {
            int value = min + source.nextInt(max - min + 1);
            System.out.print(value);
            if (i < count - 1) System.out.print(", ");
        }
        System.out.println("]");
    }
}