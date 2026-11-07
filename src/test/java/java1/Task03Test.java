package java1;

import java1.task01.CompactCourseFormatter;
import java1.task01.CourseFormatter;
import java1.task01.DetailedCourseFormatter;
import java1.task03.JsonCourseFormatter;
import model.Course;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Task03Test {

    @Test
    void jsonFormatter_returnsJsonLikeString() {
        Course course = new Course(7, "Patterns", 40);

        String result = new JsonCourseFormatter().format(course);

        assertEquals("{\"id\":7,\"title\":\"Patterns\",\"completed\":0,\"total\":40}", result);
    }

    @Test
    void formatterArray_isProcessedWithoutTypeChecks() {
        Course course = new Course(1, "Java", 32);
        CourseFormatter[] formatters = {
            new CompactCourseFormatter(),
            new DetailedCourseFormatter(),
            new JsonCourseFormatter()
        };
        String[] expected = {
            "1: Java",
            "Course[id=1, title='Java', progress=0/32, completed=false]",
            "{\"id\":1,\"title\":\"Java\",\"completed\":0,\"total\":32}"
        };

        for (int i = 0; i < formatters.length; i++) {
            assertEquals(expected[i], formatters[i].format(course));
        }
    }
}
