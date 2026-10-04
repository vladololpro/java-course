package java1.task07;

/* Контракт отправителя уведомлений.
 * Все - реальные реализации и декораторы - реализуют этот интерфейс. */
public interface NotificationSender {
    void send(String message);
}