package java1.task08;

import java.util.Optional;
import model.Course;

/* «Сломанный» вариант из условия: базовый репозиторий обещает save.
 * Наследник read-only вынужден бросить исключение. */
public class CourseRepository {

    public void save(Course course) {
        System.out.println("Saved: " + course.title());
    }

    public Optional<Course> findById(long id) {
        return Optional.empty();
    }
}
