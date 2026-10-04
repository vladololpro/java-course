package java1.task07;

/* Декоратор: логирует вызовы вокруг внутреннего отправителя.
 * Сам не наследуется от реализации, а содержит её. */
public class LoggingSender implements NotificationSender {

    private final NotificationSender inner;

    public LoggingSender(NotificationSender inner) {
        this.inner = inner;
    }

    @Override
    public void send(String message) {
        System.out.println("LOG before: " + message);
        inner.send(message);
        System.out.println("LOG after: " + message);
    }
}