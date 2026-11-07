package java1;

import java1.task09.AgePolicy;
import java1.task09.CapacityPolicy;
import java1.task09.EnrollmentPolicy;
import java1.task09.EnrollmentService;
import java1.task09.PrerequisitePolicy;
import java1.task09.Student;
import model.Course;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Task09Test {

    private final Course course = new Course(10, "Java", 32);

    @Test
    void agePolicy_checksMinimumAge() {
        AgePolicy policy = new AgePolicy(18);

        assertFalse(policy.canEnroll(new Student(1, 17), course));
        assertTrue(policy.canEnroll(new Student(2, 18), course));
    }

    @Test
    void prerequisitePolicy_checksCompletedCourse() {
        PrerequisitePolicy policy = new PrerequisitePolicy(5);
        Student student = new Student(1, 20);

        assertFalse(policy.canEnroll(student, course));

        student.completeCourse(5);

        assertTrue(policy.canEnroll(student, course));
    }

    @Test
    void capacityPolicy_respectsAvailableSeats() {
        CapacityPolicy policy = new CapacityPolicy(1);

        assertTrue(policy.canEnroll(new Student(1, 20), course));

        policy.enrollOne();

        assertFalse(policy.canEnroll(new Student(2, 20), course));
    }

    @Test
    void enrollmentService_delegatesToInjectedPolicy() {
        EnrollmentPolicy rejectAll = (student, selectedCourse) -> false;
        EnrollmentPolicy acceptAll = (student, selectedCourse) -> true;

        try (OutputCapture ignored = new OutputCapture()) {
            assertFalse(new EnrollmentService(rejectAll).enroll(new Student(1, 20), course));
            assertTrue(new EnrollmentService(acceptAll).enroll(new Student(2, 20), course));
        }
    }
}
