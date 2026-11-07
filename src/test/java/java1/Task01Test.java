package java1;

import java1.task01.CompactCourseFormatter;
import java1.task01.CourseFormatter;
import java1.task01.DetailedCourseFormatter;
import model.Course;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Task01Test {

    @Test
    void compactFormatter_returnsIdAndTitle() {
        Course course = new Course(1, "Java", 32);

        String result = new CompactCourseFormatter().format(course);

        assertEquals("1: Java", result);
    }

    @Test
    void detailedFormatter_showsProgressAndCompletion() {
        Course course = new Course(1, "Java", 32);
        CourseFormatter formatter = new DetailedCourseFormatter();

        assertEquals(
            "Course[id=1, title='Java', progress=0/32, completed=false]",
            formatter.format(course)
        );

        course.completeHours(32);

        assertTrue(formatter.format(course).contains("progress=32/32, completed=true"));
    }

    @Test
    void formatters_workThroughCommonInterface() {
        Course course = new Course(1, "Java", 32);
        CourseFormatter[] formatters = {
            new CompactCourseFormatter(),
            new DetailedCourseFormatter()
        };

        assertEquals("1: Java", formatters[0].format(course));
        assertTrue(formatters[1].format(course).startsWith("Course[id=1"));
    }
}
