package java1.task01;

import model.Course;

/* Демонстрация полиморфизма: одна переменная интерфейсного типа,
 * две разные реализации, подставляемые во время выполнения. */
public class Main {

    public static void main(String[] args) {

        /* Готовим курс: 32 часа всего, 6 уже пройдено. */
        Course course = new Course(10, "Java Fundamentals", 32);
        course.completeHours(6);

        /* Переменная типа CourseFormatter.
         * Компилятор знает только, что у неё есть метод format(Course). */
        CourseFormatter formatter;

        /* Первая реализация. */
        formatter = new CompactCourseFormatter();
        System.out.println(formatter.format(course));

        /* Вторая реализация (в ту же переменную). */
        formatter = new DetailedCourseFormatter();
        System.out.println(formatter.format(course));
    }
}