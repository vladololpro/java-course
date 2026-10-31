package java6;

import java6.domain.Course;
import java.util.List;

/* Задание 3: reduce с правильной и неправильной identity. */
public class DurationReducer {

    public int sumDurations(List<Course> courses) {
        return courses.stream()
                .map(Course::duration)
                .reduce(0, Integer::sum);
    }

    public String joinCourseCodes(List<Course> courses) {
        return courses.stream()
                .map(Course::code)
                .reduce("", DurationReducer::joinCodes);
    }

    static String joinCodes(String left, String right) {
        if (left.isEmpty()) {
            return right;
        }
        if (right.isEmpty()) {
            return left;
        }
        return left + ", " + right;
    }

    public void demo() {
        List<Course> courses = List.of(
            new Course(1, "JAVA-1", "A", 32, List.of()),
            new Course(2, "JAVA-2", "B", 40, List.of()),
            new Course(3, "SQL-1", "A", 20, List.of())
        );

        System.out.println("Правильная сумма (identity = 0): " + sumDurations(courses));

        int wrongSum = courses.stream()
                .map(Course::duration)
                .reduce(1000, Integer::sum);
        System.out.println("Неправильная сумма (identity = 1000): " + wrongSum);

        System.out.println("Правильное объединение строк (identity = \"\"): " + joinCourseCodes(courses));

        String wrongCodes = courses.stream()
                .map(Course::code)
                .reduce("JAVA-1", DurationReducer::joinCodes);
        System.out.println("Неправильное объединение строк (identity = \"JAVA-1\"): " + wrongCodes);

        String part1 = joinCodes(joinCodes("JAVA-1", "JAVA-2"), "SQL-1");
        String part2 = joinCodes("JAVA-1", joinCodes("JAVA-2", "SQL-1"));
        System.out.println("Ассоциативность accumulator: " + part1.equals(part2));
    }
}
