package java1.task10;

/* Разрешение конфликта: явный override.
 * Здесь выбираем поведение Notifier. */
public class FixedBoth implements Notifier, Logger {

    @Override
    public String describe() {
        return Notifier.super.describe();
    }
}