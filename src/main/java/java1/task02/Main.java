package java1.task02;

/* Демонстрация: override решается JVM по динамическому типу,
 * overload — компилятором по статическому типу. */
public class Main {

    public static void main(String[] args) {

        NotificationSender asBase = new EmailSender();
        EmailSender asEmail = new EmailSender();

        /* ---- Override: результат зависит от динамического типа ---- */
        asBase.send("hello");
        asEmail.send("hello");

        /* ---- Overload: результат зависит от статического типа ---- */
        asBase.send("hello", 5);
        asEmail.send("hello", 5);

        /* ---- Проверка: у ConsoleSender другая реализация send(String) ---- */
        NotificationSender asConsole = new ConsoleSender();
        asConsole.send("hello");
    }
}