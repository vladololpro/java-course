package java1.task08;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import model.Course;

/* Полная реализация: и чтение, и запись. */
public class InMemoryCourseRepository implements FullCourseRepository {

    private final Map<Long, Course> storage = new HashMap<>();

    @Override
    public Optional<Course> findById(long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public void save(Course course) {
        storage.put(course.id(), course);
    }
}