package java4.numbers;

/* Класс для демонстрации autoboxing, unboxing и кэша Integer. */
public class IntegerPolicy {

    public void run() {
        System.out.println("=== 1. Кэш Integer (от -128 до 127) ===");
        Integer a = 100;
        Integer b = 100;
        System.out.println("Integer a = 100, b = 100");
        System.out.println("a == b: " + (a == b));
        System.out.println("a.equals(b): " + a.equals(b));

        Integer c = 1000;
        Integer d = 1000;
        System.out.println("\nInteger c = 1000, d = 1000");
        System.out.println("c == d: " + (c == d));
        System.out.println("c.equals(d): " + c.equals(d));

        System.out.println("\n=== 2. NullPointerException при unboxing ===");
        Integer empty = null;
        try {
            int result = empty + 5;
            System.out.println("result: " + result);
        } catch (NullPointerException e) {
            System.out.println("Поймали NPE! Переменная была null.");
        }

        System.out.println("\n=== 3. Правильный контракт ===");
        Integer safe = null;
        if (safe != null) {
            int result = safe + 5;
            System.out.println("result: " + result);
        } else {
            System.out.println("safe is null, избежали NPE.");
        }

        System.out.println("\n=== 4. Правильное сравнение оберток ===");
        Integer x = 1000;
        Integer y = 1000;
        if (x.equals(y)) {
            System.out.println("x и y равны по значению (использован equals).");
        }
    }
}