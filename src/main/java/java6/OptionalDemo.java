package java6;

import java6.domain.Course;
import java.util.List;
import java.util.Optional;

/* Задание 5: Работа с Optional. */
public class OptionalDemo {

    public void demo() {
        List<Course> courses = List.of(
            new Course(1, "JAVA-1", "A", 32, List.of()),
            new Course(2, "JAVA-2", "B", 40, List.of()),
            new Course(3, "SQL-1", "A", 20, List.of())
        );

        List<Course> emptyList = List.of();

        System.out.println("1. Поиск самого длинного курса (есть данные):");
        Optional<Course> longest = courses.stream()
                .max(java.util.Comparator.comparingInt(Course::duration));
        
        String result = longest
                .map(c -> c.code() + " (" + c.duration() + "h)")
                .orElse("Нет курсов");
        System.out.println("  Самый длинный: " + result);

        System.out.println("\n2. Поиск самого длинного курса (пустой список):");
        Optional<Course> longestEmpty = emptyList.stream()
                .max(java.util.Comparator.comparingInt(Course::duration));
        
        String fallback = longestEmpty
                .map(c -> c.code() + " (" + c.duration() + "h)")
                .orElse("Нет курсов");
        System.out.println("  Результат: " + fallback);
    }
}