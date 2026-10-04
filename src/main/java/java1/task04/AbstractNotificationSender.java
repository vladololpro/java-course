package java1.task04;

/* Абстрактный базовый отправитель.
 * Содержит общий алгоритм отправки и абстрактную точку расширения doSend. */
public abstract class AbstractNotificationSender {

    private final String recipient;

    protected AbstractNotificationSender(String recipient) {
        this.recipient = recipient;
    }

    public final void send(String message) {
        if (recipient == null || recipient.isBlank()) {
            System.out.println("ERR: recipient is empty");
            return;
        }
        System.out.println("Sending to " + recipient + "...");
        doSend(message);
        System.out.println("Done.");
    }

    protected abstract void doSend(String message);

    protected String recipient() {
        return recipient;
    }
}