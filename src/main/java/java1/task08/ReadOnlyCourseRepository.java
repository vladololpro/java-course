package java1.task08;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import model.Course;

/* Read-only реализация. Реализует только ReadableCourseRepository,
 * поэтому не обещает save - и не нарушает LSP. */
public class ReadOnlyCourseRepository implements ReadableCourseRepository {

    private final Map<Long, Course> storage = new HashMap<>();

    public ReadOnlyCourseRepository() {
    }

    public void seed(Course course) {
        storage.put(course.id(), course);
    }

    @Override
    public Optional<Course> findById(long id) {
        return Optional.ofNullable(storage.get(id));
    }
}