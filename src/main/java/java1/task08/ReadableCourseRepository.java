package java1.task08;

import java.util.Optional;
import model.Course;

/* Контракт «только чтение». Ничего не обещает про запись. */
public interface ReadableCourseRepository {

    Optional<Course> findById(long id);
}