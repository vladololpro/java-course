package java6;

import java6.domain.Course;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Task5OptionalDemoTest {

    private final OptionalDemo optionalDemo = new OptionalDemo();

    @Test
    void findsLongestCourse() {
        var result = optionalDemo.findLongest(courses());

        assertTrue(result.isPresent());
        assertEquals("JAVA-2", result.orElseThrow().code());
    }

    @Test
    void returnsEmptyOptionalForEmptyList() {
        assertTrue(optionalDemo.findLongest(List.of()).isEmpty());
    }

    @Test
    void transformsLongestCourseAndUsesFallback() {
        assertEquals("JAVA-2 (40h)", optionalDemo.describeLongest(courses()));
        assertEquals("Нет курсов", optionalDemo.describeLongest(List.of()));
    }

    private List<Course> courses() {
        return List.of(
                new Course(1, "JAVA-1", "A", 32, List.of()),
                new Course(2, "JAVA-2", "B", 40, List.of()),
                new Course(3, "SQL-1", "A", 20, List.of())
        );
    }
}
