package java1.task08;

import model.Course;

/* Контракт «запись». Только save. */
public interface WritableCourseRepository {

    void save(Course course);
}