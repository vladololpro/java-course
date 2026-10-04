package java1.task13;

/* Реализация: email. Знает только про email. */
public class EmailSender implements NotificationSender {

    private final String address;

    public EmailSender(String address) {
        this.address = address;
    }

    @Override
    public void send(String message) {
        System.out.println("[EMAIL to " + address + "] " + message);
    }
}