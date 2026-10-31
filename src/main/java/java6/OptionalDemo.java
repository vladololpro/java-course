package java6;

import java6.domain.Course;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/* Задание 5: Работа с Optional. */
public class OptionalDemo {

    public Optional<Course> findLongest(List<Course> courses) {
        return courses.stream()
                .max(Comparator.comparingInt(Course::duration));
    }

    public String describeLongest(List<Course> courses) {
        return findLongest(courses)
                .map(course -> course.code() + " (" + course.duration() + "h)")
                .orElse("Нет курсов");
    }

    public void demo() {
        List<Course> courses = List.of(
            new Course(1, "JAVA-1", "A", 32, List.of()),
            new Course(2, "JAVA-2", "B", 40, List.of()),
            new Course(3, "SQL-1", "A", 20, List.of())
        );

        List<Course> emptyList = List.of();

        System.out.println("1. Поиск самого длинного курса (есть данные):");
        System.out.println("  Самый длинный: " + describeLongest(courses));

        System.out.println("\n2. Поиск самого длинного курса (пустой список):");
        System.out.println("  Результат: " + describeLongest(emptyList));
    }
}
