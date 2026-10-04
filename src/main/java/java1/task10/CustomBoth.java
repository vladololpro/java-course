package java1.task10;

/* Третий вариант: полностью своя реализация.
 * default-методы интерфейсов игнорируются. */
public class CustomBoth implements Notifier, Logger {

    @Override
    public String describe() {
        return "Custom";
    }
}