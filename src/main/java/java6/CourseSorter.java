package java6;

import java6.domain.Course;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/* Задание 1: Сортировка с детерминизмом. */
public class CourseSorter {
    private static final Comparator<Course> COURSE_COMPARATOR = 
        Comparator.comparing(Course::level)
                  .thenComparing(Course::duration)
                  .thenComparing(Course::code);

    public List<Course> sortCourses(List<Course> courses) {
        return courses.stream()
                .sorted(COURSE_COMPARATOR)
                .collect(Collectors.toList());
    }

    public void demo() {
        List<Course> list1 = List.of(
            new Course(1, "JAVA-2", "B", 40),
            new Course(2, "JAVA-1", "A", 32),
            new Course(3, "WEB-1", "A", 32), 
            new Course(4, "SQL-1", "B", 20)
        );

        List<Course> list2 = List.of(
            new Course(4, "SQL-1", "B", 20),
            new Course(3, "WEB-1", "A", 32),
            new Course(2, "JAVA-1", "A", 32),
            new Course(1, "JAVA-2", "B", 40)
        );

        List<Course> sorted1 = sortCourses(list1);
        List<Course> sorted2 = sortCourses(list2);

        System.out.println("Отсортированный список 1:");
        sorted1.forEach(System.out::println);
        
        System.out.println("\nОтсортированный список 2:");
        sorted2.forEach(System.out::println);
        
        System.out.println("\nРезультаты идентичны: " + sorted1.equals(sorted2));
    }
}