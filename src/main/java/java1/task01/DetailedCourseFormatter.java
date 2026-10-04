package java1.task01;

import model.Course;

/* Подробная реализация: id, название, прогресс, признак завершения. */
public class DetailedCourseFormatter implements CourseFormatter {

    @Override
    public String format(Course course) {
        return String.format(
            "Course[id=%d, title='%s', progress=%d/%d, completed=%b]",
            course.id(),
            course.title(),
            course.completedHours(),
            course.durationHours(),
            course.isCompleted()
        );
    }
}