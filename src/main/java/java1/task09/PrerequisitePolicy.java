package java1.task09;

import model.Course;

/* Политика по предварительным курсам: студент должен пройти prerequisite. */
public class PrerequisitePolicy implements EnrollmentPolicy {

    private final long requiredCourseId;

    public PrerequisitePolicy(long requiredCourseId) {
        this.requiredCourseId = requiredCourseId;
    }

    @Override
    public boolean canEnroll(Student student, Course course) {
        return student.hasCompleted(requiredCourseId);
    }
}