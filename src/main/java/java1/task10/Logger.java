package java1.task10;

/* Второй интерфейс с default-методом с той же сигнатурой. */
public interface Logger {

    default String describe() {
        return "Logger";
    }
}