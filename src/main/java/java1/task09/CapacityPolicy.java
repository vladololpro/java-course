package java1.task09;

import model.Course;

/* Политика по вместимости: на курсе должны быть свободные места. */
public class CapacityPolicy implements EnrollmentPolicy {

    private final int capacity;
    private int enrolled;

    public CapacityPolicy(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public boolean canEnroll(Student student, Course course) {
        return enrolled < capacity;
    }

    public void enrollOne() {
        enrolled++;
    }
}