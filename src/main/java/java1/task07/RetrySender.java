package java1.task07;

/* Декоратор: выполняет отправку несколько раз.
 * Полезно, если внутренний отправитель может «упасть». */
public class RetrySender implements NotificationSender {

    private final NotificationSender inner;
    private final int attempts;

    public RetrySender(NotificationSender inner, int attempts) {
        this.inner = inner;
        this.attempts = attempts;
    }

    @Override
    public void send(String message) {
        for (int i = 1; i <= attempts; i++) {
            System.out.println("RETRY attempt " + i + "/" + attempts);
            inner.send(message);
        }
    }
}