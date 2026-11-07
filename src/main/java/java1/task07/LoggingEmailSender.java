package java1.task07;

/* Старый вариант «до рефакторинга»: логирование через наследование.
 * От него в задании нужно отказаться в пользу декоратора LoggingSender. */
public class LoggingEmailSender extends EmailSender {

    @Override
    public void send(String message) {
        System.out.println("LOG before (inheritance): " + message);
        super.send(message);
        System.out.println("LOG after (inheritance): " + message);
    }
}
