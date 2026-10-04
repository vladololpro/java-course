package java1.task03;

import model.Course;
import java1.task01.CourseFormatter;
import java1.task01.CompactCourseFormatter;
import java1.task01.DetailedCourseFormatter;

/* Демонстрация полиморфного массива: массив интерфейсного типа
 * хранит разные реализации, обход не знает о конкретных классах. */
public class Main {

    public static void main(String[] args) {

        Course course = new Course(10, "Java Fundamentals", 32);
        course.completeHours(6);

        CourseFormatter[] formatters = {
            new CompactCourseFormatter(),
            new DetailedCourseFormatter(),
            new JsonCourseFormatter()
        };

        for (CourseFormatter formatter : formatters) {
            System.out.println(formatter.format(course));
        }
    }
}