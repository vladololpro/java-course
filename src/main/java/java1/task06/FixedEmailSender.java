package java1.task06;

/* Наследник с корректным override. @Override гарантирует,
 * что мы действительно переопределяем родительский метод. */
public class FixedEmailSender extends FixedSender {

    @Override
    public String send(String message) {
        return "email.send: " + message;
    }
}