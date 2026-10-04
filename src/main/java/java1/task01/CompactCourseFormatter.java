package java1.task01;

import model.Course;

/* Компактная реализация контракта: id и название через двоеточие. */
public class CompactCourseFormatter implements CourseFormatter {

    /* Переопределяем метод интерфейса. */
    @Override
    public String format(Course course) {
        return course.id() + ": " + course.title();
    }
}