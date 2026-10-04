package java1.task02;

/* Базовый отправитель уведомлений.
 * Метод send(String) будет переопределён в наследниках (override). */
public class NotificationSender {

    /* Будет переопределён в EmailSender и ConsoleSender. */
    public void send(String message) {
        System.out.println("NotificationSender.send: " + message);
    }

    /* Перегруженный метод (overload): то же имя, другой набор параметров. */
    public void send(String message, int priority) {
        System.out.println("NotificationSender.send(priority=" + priority + "): " + message);
    }
}