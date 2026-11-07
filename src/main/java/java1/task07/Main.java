package java1.task07;

/* Демонстрация декораторов и двух порядков композиции. */
public class Main {

    public static void main(String[] args) {

        NotificationSender email = new EmailSender();

        System.out.println("=== Было: LoggingEmailSender extends EmailSender ===");
        NotificationSender oldStyle = new LoggingEmailSender();
        oldStyle.send("hello");
        System.out.println();

        System.out.println("=== Logging(Retry(Flaky(Email))) ===");
        NotificationSender order1 = new LoggingSender(
            new RetrySender(new FlakySender(email, 1), 3)
        );
        order1.send("hello");
        System.out.println();

        System.out.println("=== Retry(Logging(Flaky(Email))) ===");
        NotificationSender order2 = new RetrySender(
            new LoggingSender(new FlakySender(email, 1)), 3
        );
        order2.send("hello");
        System.out.println();

        System.out.println("=== Metrics(Logging(Email)) ===");
        MetricsSender metrics = new MetricsSender(
            new LoggingSender(email)
        );
        metrics.send("first");
        metrics.send("second");
        System.out.println("Metrics count = " + metrics.count());
    }
}