package java1.task04;

/* Console-отправитель. Реализует doSend по-своему. */
public class ConsoleSender extends AbstractNotificationSender {

    public ConsoleSender(String recipient) {
        super(recipient);
    }

    @Override
    protected void doSend(String message) {
        System.out.println("[CONSOLE -> " + recipient() + "] " + message);
    }
}