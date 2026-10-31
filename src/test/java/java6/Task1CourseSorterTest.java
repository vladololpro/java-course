package java6;

import java6.domain.Course;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Task1CourseSorterTest {

    private final CourseSorter sorter = new CourseSorter();

    @Test
    void sortingIsDeterministicWhenMainKeysAreEqual() {
        Course second = new Course(2, "SAME", "A", 10, List.of());
        Course first = new Course(1, "SAME", "A", 10, List.of());
        Course shortest = new Course(3, "SAME", "A", 9, List.of());

        List<Course> expected = List.of(shortest, first, second);

        assertEquals(expected, sorter.sortCourses(List.of(second, first, shortest)));
        assertEquals(expected, sorter.sortCourses(List.of(first, shortest, second)));
    }

    @Test
    void resultDoesNotDependOnInputOrder() {
        List<Course> firstOrder = List.of(
                new Course(1, "JAVA-2", "B", 40, List.of()),
                new Course(2, "JAVA-1", "A", 32, List.of()),
                new Course(3, "WEB-1", "A", 32, List.of()),
                new Course(4, "SQL-1", "B", 20, List.of())
        );

        List<Course> secondOrder = List.of(
                new Course(4, "SQL-1", "B", 20, List.of()),
                new Course(3, "WEB-1", "A", 32, List.of()),
                new Course(2, "JAVA-1", "A", 32, List.of()),
                new Course(1, "JAVA-2", "B", 40, List.of())
        );

        assertEquals(sorter.sortCourses(firstOrder), sorter.sortCourses(secondOrder));
    }
}
