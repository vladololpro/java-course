package java1.task13;

/* Сломанный вариант: type code + switch.
 * Один класс знает про все типы уведомлений. */
public class BrokenNotification {

    private final String type;
    private final String recipient;

    public BrokenNotification(String type, String recipient) {
        this.type = type;
        this.recipient = recipient;
    }

    public void send(String message) {
        switch (type) {
            case "email":
                System.out.println("[EMAIL to " + recipient + "] " + message);
                break;
            case "sms":
                System.out.println("[SMS to " + recipient + "] " + message);
                break;
            case "push":
                System.out.println("[PUSH to " + recipient + "] " + message);
                break;
            default:
                System.out.println("Unknown type: " + type);
        }
    }
}