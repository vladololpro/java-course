package java1.task07;

/* Отправитель, который может временно «упасть».
 * Нужен, чтобы показать, что RetrySender действительно повторяет
 * отправку после ошибки, а не просто вызывает send несколько раз. */
public class FlakySender implements NotificationSender {

    private final NotificationSender inner;
    private int failuresLeft;

    public FlakySender(NotificationSender inner, int failuresLeft) {
        this.inner = inner;
        this.failuresLeft = failuresLeft;
    }

    @Override
    public void send(String message) {
        if (failuresLeft > 0) {
            failuresLeft--;
            throw new IllegalStateException("temporary failure");
        }
        inner.send(message);
    }
}
