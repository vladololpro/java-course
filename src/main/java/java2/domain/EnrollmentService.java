package java2.domain;

import java2.errors.EnrollmentRejectedException;

public class EnrollmentService {

    public void enroll(long studentId, long courseId, CourseStatus status, int availableSeats, boolean hasPrerequisite, boolean alreadyEnrolled) {
        
        if (studentId <= 0 || courseId <= 0) {
            throw new NullPointerException("StudentId and CourseId must be positive");
        }

        if (status == CourseStatus.ARCHIVED) {
            throw new EnrollmentRejectedException(studentId, courseId, "COURSE_ARCHIVED");
        }
        if (status == CourseStatus.CLOSED) {
            throw new EnrollmentRejectedException(studentId, courseId, "COURSE_CLOSED");
        }
        if (alreadyEnrolled) {
            throw new EnrollmentRejectedException(studentId, courseId, "ALREADY_ENROLLED");
        }
        if (!hasPrerequisite) {
            throw new EnrollmentRejectedException(studentId, courseId, "PREREQUISITE_MISSING");
        }
        if (availableSeats <= 0) {
            throw new EnrollmentRejectedException(studentId, courseId, "NO_SEATS");
        }
    }
}