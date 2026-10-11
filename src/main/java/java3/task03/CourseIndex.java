package java3.task03;

import java.util.HashMap;
import java.util.Map;
import model.Course;

/* Индекс CourseId -> Course на HashMap.
 * Демонстрирует разницу между get, containsKey, put,
 * putIfAbsent и computeIfAbsent. */
public class CourseIndex {

    private final Map<Long, Course> index = new HashMap<>();

    public boolean addSafely(Course course) {
        Course previous = index.putIfAbsent(course.id(), course);
        return previous == null;
    }

    public Course get(long id) {
        return index.get(id);
    }

    public boolean contains(long id) {
        return index.containsKey(id);
    }

    public Course putIfAbsent(long id, Course course) {
        return index.putIfAbsent(id, course);
    }

    public Course computeIfAbsent(long id, String title) {
        return index.computeIfAbsent(id, key -> new Course(key, title, 10));
    }

    public int size() {
        return index.size();
    }
}