package java1.task06;

/* Исправленная иерархия: везде @Override, сигнатуры совпадают. */
public class FixedSender {

    public String send(String message) {
        return "base.send: " + message;
    }
}