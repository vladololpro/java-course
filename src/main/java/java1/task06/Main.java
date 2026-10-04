package java1.task06;

/* Демонстрация:
 * 1) без @Override «override» может оказаться перегрузкой;
 * 2) как @Override ловит ошибку на этапе компиляции. */
public class Main {

    public static void main(String[] args) {

        BrokenSender broken = new BrokenEmailSender();
        System.out.println(broken.send("hello"));

        FixedSender fixed = new FixedEmailSender();
        System.out.println(fixed.send("hello"));
    }
}