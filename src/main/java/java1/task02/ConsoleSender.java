package java1.task02;

/* Наследник, который переопределяет send(String). */
public class ConsoleSender extends NotificationSender {

    @Override
    public void send(String message) {
        System.out.println("ConsoleSender.send: " + message);
    }
}