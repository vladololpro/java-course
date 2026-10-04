package java1.task07;

/* Реальная реализация: вывод в консоль. */
public class ConsoleSender implements NotificationSender {

    @Override
    public void send(String message) {
        System.out.println("  [CONSOLE] " + message);
    }
}