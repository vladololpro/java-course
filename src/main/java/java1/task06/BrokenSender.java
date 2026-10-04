package java1.task06;

/* Демонстрация ошибок, которые не ловятся компилятором
 * без @Override. Методы кажутся переопределением,
 * но фактически это перегрузка или вообще новый метод. */
public class BrokenSender {

    public String send(String message) {
        return "base.send: " + message;
    }
}