package java1.task10;

/* Демонстрация разрешения конфликта default-методов. */
public class Main {

    public static void main(String[] args) {
        System.out.println(new FixedBoth().describe());
        System.out.println(new CombinedBoth().describe());
        System.out.println(new CustomBoth().describe());
    }
}