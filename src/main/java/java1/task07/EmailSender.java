package java1.task07;

/* Реальная реализация: отправка email. */
public class EmailSender implements NotificationSender {

    @Override
    public void send(String message) {
        System.out.println("  [EMAIL] " + message);
    }
}