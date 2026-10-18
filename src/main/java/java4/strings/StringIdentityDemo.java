package java4.strings;

/* Класс для демонстрации сравнения строк через == и equals. */
public class StringIdentityDemo {

    public void run() {
        System.out.println("=== 1. Литералы и == ===");
        String s1 = "Java";
        String s2 = "Java";
        System.out.println("s1 == s2: " + (s1 == s2));
        System.out.println("s1.equals(s2): " + s1.equals(s2));

        System.out.println("\n=== 2. new String ===");
        String s3 = new String("Java");
        System.out.println("s1 == s3: " + (s1 == s3));
        System.out.println("s1.equals(s3): " + s1.equals(s3));

        System.out.println("\n=== 3. Конкатенация compile-time ===");
        String s4 = "Ja" + "va";
        System.out.println("s1 == s4: " + (s1 == s4));
        System.out.println("s1.equals(s4): " + s1.equals(s4));

        System.out.println("\n=== 4. Конкатенация runtime ===");
        String part1 = "Ja";
        String part2 = "va";
        String s5 = part1 + part2;
        System.out.println("s1 == s5: " + (s1 == s5));
        System.out.println("s1.equals(s5): " + s1.equals(s5));

        System.out.println("\n=== 5. intern ===");
        String s6 = s5.intern();
        System.out.println("s1 == s6: " + (s1 == s6));
    }
}