package java1.task03;

import model.Course;
import java1.task01.CourseFormatter;

/* Третья реализация контракта: JSON-подобная строка. */
public class JsonCourseFormatter implements CourseFormatter {

    @Override
    public String format(Course course) {
        return String.format(
            "{\"id\":%d,\"title\":\"%s\",\"completed\":%d,\"total\":%d}",
            course.id(),
            course.title(),
            course.completedHours(),
            course.durationHours()
        );
    }
}