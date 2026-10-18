package java4.strings;

/* Класс для демонстрации сборки строки через + и StringBuilder. */
public class ReportBuilder {

    public void run() {
        int count = 10_000;

        System.out.println("=== Сборка через + ===");
        long start1 = System.nanoTime();
        String result1 = "";
        for (int i = 0; i < count; i++) {
            result1 = result1 + "Line " + i + "\n";
        }
        long end1 = System.nanoTime();
        long time1 = (end1 - start1) / 1_000_000;
        System.out.println("Время: " + time1 + " мс");
        System.out.println("Длина строки: " + result1.length());

        System.out.println("\n=== Сборка через StringBuilder ===");
        long start2 = System.nanoTime();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append("Line ").append(i).append("\n");
        }
        String result2 = sb.toString();
        long end2 = System.nanoTime();
        long time2 = (end2 - start2) / 1_000_000;
        System.out.println("Время: " + time2 + " мс");
        System.out.println("Длина строки: " + result2.length());

        System.out.println("\nРезультаты идентичны: " + result1.equals(result2));
    }
}