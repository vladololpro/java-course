package java1.task04;

/* Демонстрация шаблонного метода:
 * send() - общий алгоритм в родителе,
 * doSend() - точка расширения в наследниках. */
public class Main {

    public static void main(String[] args) {

        AbstractNotificationSender email   = new EmailSender("student@example.com");
        AbstractNotificationSender console = new ConsoleSender("local");

        email.send("Your course is ready");
        System.out.println("---");
        console.send("Your course is ready");
        System.out.println("---");

        AbstractNotificationSender bad = new EmailSender("   ");
        bad.send("This will not be sent");
    }
}