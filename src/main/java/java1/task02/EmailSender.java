package java1.task02;

/* Наследник, который переопределяет send(String). */
public class EmailSender extends NotificationSender {

    @Override
    public void send(String message) {
        System.out.println("EmailSender.send: " + message);
    }
}