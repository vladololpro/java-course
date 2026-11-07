package java1.task08;

import model.Course;

/* Нарушение LSP: наследник обещает save, но не может его выполнить. */
public class BrokenReadOnlyCourseRepository extends CourseRepository {

    @Override
    public void save(Course course) {
        throw new UnsupportedOperationException("read-only repository cannot save");
    }
}
