package java4.domain;

import java.util.Locale;

/* Класс для нормализации названий курсов (display value + search key). */
public class CourseTitle {

    private final String displayValue;
    private final String searchKey;

    public CourseTitle(String raw) {
        this.displayValue = normalizeDisplay(raw);
        this.searchKey = normalizeSearch(raw);
    }

    private String normalizeDisplay(String raw) {
        String trimmed = raw.strip();
        return trimmed.replaceAll("\\s+", " ");
    }

    private String normalizeSearch(String raw) {
        String display = normalizeDisplay(raw);
        return display.toLowerCase(Locale.ROOT);
    }

    public String getDisplayValue() {
        return displayValue;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void run() {
        System.out.println("=== 1. Нормализация одной строки ===");
        CourseTitle title1 = new CourseTitle("  Java   Basics  ");
        System.out.println("Исходное:       \"  Java   Basics  \"");
        System.out.println("Display Value:  \"" + title1.getDisplayValue() + "\"");
        System.out.println("Search Key:     \"" + title1.getSearchKey() + "\"");

        System.out.println("\n=== 2. Конфликт двух разных строк ===");
        CourseTitle titleA = new CourseTitle("Java Course");
        CourseTitle titleB = new CourseTitle(" java   course ");
        
        System.out.println("Строка A:       \"" + titleA.getDisplayValue() + "\"");
        System.out.println("Строка B:       \"" + titleB.getDisplayValue() + "\"");
        System.out.println("Search Key A:   \"" + titleA.getSearchKey() + "\"");
        System.out.println("Search Key B:   \"" + titleB.getSearchKey() + "\"");
        System.out.println("Ключи совпадают: " + titleA.getSearchKey().equals(titleB.getSearchKey()));

        System.out.println("\n=== 3. Важность Locale ===");
        System.out.println("В турецком языке буква 'I' переводится в нижний регистр как 'ı'");
        System.out.println("Поэтому мы используем Locale.ROOT для предсказуемости.");
    }
}