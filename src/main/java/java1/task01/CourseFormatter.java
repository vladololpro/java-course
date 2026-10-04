package java1.task01;

import model.Course;

/* Контракт форматтера: преобразовать курс в строку.
 * Интерфейс описывает что делает форматтер, но не как. */
public interface CourseFormatter {

    /* Единственный метод контракта.
     * Любой, кто реализует CourseFormatter, обязан его предоставить. */
    String format(Course course);
}