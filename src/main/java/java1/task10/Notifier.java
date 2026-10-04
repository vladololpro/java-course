package java1.task10;

/* Первый интерфейс с default-методом. */
public interface Notifier {

    default String describe() {
        return "Notifier";
    }
}