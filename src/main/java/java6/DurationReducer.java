package java6;

import java6.domain.Course;
import java.util.List;

/* Задание 3: reduce с правильной и неправильной identity. */
public class DurationReducer {

    public void demo() {
        List<Course> courses = List.of(
            new Course(1, "JAVA-1", "A", 32, List.of()),
            new Course(2, "JAVA-2", "B", 40, List.of()),
            new Course(3, "SQL-1", "A", 20, List.of())
        );

        int correctSum = courses.stream()
                .map(Course::duration)
                .reduce(0, Integer::sum);
        System.out.println("Правильная сумма (identity = 0): " + correctSum);

        int wrongSum = courses.stream()
                .map(Course::duration)
                .reduce(1000, Integer::sum);
        System.out.println("Неправильная сумма (identity = 1000): " + wrongSum);

        int part1 = (32 + 40) + 20;
        int part2 = 32 + (40 + 20);
        System.out.println("Ассоциативность: " + (part1 == part2));
    }
}