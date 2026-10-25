package java2.errors;

import java.util.Arrays;
import java.util.List;

/* Классификация ситуаций по типам исключений. */
public class ExceptionClassifier {

    public void run() {
        List<String> situations = Arrays.asList(
            "Неверный аргумент (age = -5)",
            "Отсутствующий файл (config.txt)",
            "Нарушение состояния (баланс < 0)",
            "Сетевой отказ (timeout)",
            "Ошибка конфигурации (нет URL)",
            "Недостаток памяти (OutOfMemory)",
            "Ошибка парсинга числа (NumberFormat)",
            "Файл занят другим процессом (IOException)",
            "Деление на ноль",
            "Ссылка на null"
        );

        for (String s : situations) {
            System.out.println("Ситуация: " + s);
            System.out.println("Решение: " + classify(s));
            System.out.println();
        }
    }

    private String classify(String situation) {
        if (situation.contains("аргумент")) {
            return "Unchecked (IllegalArgumentException) - баг программиста";
        } else if (situation.contains("файл (config")) {
            return "Checked (FileNotFoundException) - можно восстановить (создать файл)";
        } else if (situation.contains("Нарушение")) {
            return "Unchecked (IllegalStateException) - объект в неверном состоянии";
        } else if (situation.contains("Сетевой")) {
            return "Checked (IOException) - можно повторить попытку";
        } else if (situation.contains("конфигурации")) {
            return "Checked (IOException) - прервать запуск и попросить проверить";
        } else if (situation.contains("памяти")) {
            return "Error (OutOfMemoryError) - восстановление невозможно";
        } else if (situation.contains("парсинга")) {
            return "Unchecked (NumberFormatException) - неверные данные от пользователя";
        } else if (situation.contains("занят")) {
            return "Checked (IOException) - закрыть файл и повторить";
        } else if (situation.contains("Деление")) {
            return "Unchecked (ArithmeticException) - баг в логике";
        } else if (situation.contains("null")) {
            return "Unchecked (NullPointerException) - баг в коде";
        }
        return "Неизвестно";
    }
}