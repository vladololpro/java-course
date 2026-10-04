package java1.task09;

import model.Course;

/* Политика по возрасту: студент должен быть не младше minAge. */
public class AgePolicy implements EnrollmentPolicy {

    private final int minAge;

    public AgePolicy(int minAge) {
        this.minAge = minAge;
    }

    @Override
    public boolean canEnroll(Student student, Course course) {
        return student.age() >= minAge;
    }
}