package java1.task10;

/* Другой вариант: комбинируем поведения обоих интерфейсов. */
public class CombinedBoth implements Notifier, Logger {

    @Override
    public String describe() {
        return Notifier.super.describe()
            + "+" + Logger.super.describe();
    }
}