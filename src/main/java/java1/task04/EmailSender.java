package java1.task04;

/* Email-отправитель. Реализует doSend - конкретный способ отправки. */
public class EmailSender extends AbstractNotificationSender {

    public EmailSender(String recipient) {
        super(recipient);
    }

    @Override
    protected void doSend(String message) {
        System.out.println("[EMAIL to " + recipient() + "] " + message);
    }
}