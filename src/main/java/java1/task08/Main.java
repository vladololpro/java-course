package java1.task08;

import model.Course;

/* Демонстрация:
 * - клиент чтения работает с любым ReadableCourseRepository;
 * - клиент записи требует WritableCourseRepository;
 * - read-only реализация не может попасть туда, где нужен save -
 *   это ошибка комплияции, а не исключение в runtime. */
public class Main {

    static void printCourse(ReadableCourseRepository repo, long id) {
        repo.findById(id).ifPresentOrElse(
            c -> System.out.println("Found: " + c.title()),
            () -> System.out.println("Not found: id=" + id)
        );
    }

    static void addCourse(WritableCourseRepository repo, Course course) {
        repo.save(course);
        System.out.println("Saved: " + course.title());
    }

    public static void main(String[] args) {

        ReadOnlyCourseRepository readOnly = new ReadOnlyCourseRepository();
        readOnly.seed(new Course(1, "Java Fundamentals", 32));

        InMemoryCourseRepository full = new InMemoryCourseRepository();
        full.save(new Course(2, "Design Patterns", 40));

        System.out.println("--- read-only ---");
        printCourse(readOnly, 1);
        printCourse(readOnly, 99);

        System.out.println("--- full ---");
        printCourse(full, 2);
        printCourse(full, 99);

        System.out.println("--- write to full ---");
        addCourse(full, new Course(3, "Refactoring", 50));
    }
}