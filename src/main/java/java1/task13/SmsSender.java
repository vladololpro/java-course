package java1.task13;

/* Реализация: SMS. Знает только про SMS. */
public class SmsSender implements NotificationSender {

    private final String phone;

    public SmsSender(String phone) {
        this.phone = phone;
    }

    @Override
    public void send(String message) {
        System.out.println("[SMS to " + phone + "] " + message);
    }
}