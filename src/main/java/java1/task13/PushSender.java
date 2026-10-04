package java1.task13;

/* Реализация: push. Знает только про push. */
public class PushSender implements NotificationSender {

    private final String deviceId;

    public PushSender(String deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public void send(String message) {
        System.out.println("[PUSH to " + deviceId + "] " + message);
    }
}