package java1.task07;

/* Декоратор: считает, сколько раз был вызван send. */
public class MetricsSender implements NotificationSender {

    private final NotificationSender inner;
    private int count = 0;

    public MetricsSender(NotificationSender inner) {
        this.inner = inner;
    }

    @Override
    public void send(String message) {
        count++;
        inner.send(message);
    }

    public int count() {
        return count;
    }
}