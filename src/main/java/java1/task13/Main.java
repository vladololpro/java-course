package java1.task13;

/* Сравнение: сломанный type code и исправленный полиморфизм. */
public class Main {

    public static void main(String[] args) {

        System.out.println("--- Broken: type code + switch ---");
        BrokenNotification b1 = new BrokenNotification("email", "a@b.c");
        BrokenNotification b2 = new BrokenNotification("sms", "+7900");
        BrokenNotification b3 = new BrokenNotification("push", "device-1");
        b1.send("hello");
        b2.send("hello");
        b3.send("hello");
        System.out.println();


        System.out.println("--- Fixed: полиморфизм ---");
        NotificationSender[] senders = {
            new EmailSender("a@b.c"),
            new SmsSender("+7900"),
            new PushSender("device-1")
        };
        for (NotificationSender sender : senders) {
            sender.send("hello");
        }
    }
}