package java2.domain;

import java2.errors.EnrollmentRejectedException;
import java2.errors.ErrorCode;

public class EnrollmentService {

    public void enroll(long studentId, long courseId, CourseStatus status, int availableSeats, boolean hasPrerequisite, boolean alreadyEnrolled) {
        
        if (studentId <= 0 || courseId <= 0) {
            throw new IllegalArgumentException(ErrorCode.INVALID_STUDENT_OR_COURSE + ": studentId and courseId must be positive");
        }

        if (status == CourseStatus.ARCHIVED) {
            throw new EnrollmentRejectedException(studentId, courseId, ErrorCode.COURSE_ARCHIVED);
        }
        if (status == CourseStatus.CLOSED) {
            throw new EnrollmentRejectedException(studentId, courseId, ErrorCode.COURSE_CLOSED);
        }
        if (alreadyEnrolled) {
            throw new EnrollmentRejectedException(studentId, courseId, ErrorCode.ALREADY_ENROLLED);
        }
        if (!hasPrerequisite) {
            throw new EnrollmentRejectedException(studentId, courseId, ErrorCode.PREREQUISITE_MISSING);
        }
        if (availableSeats <= 0) {
            throw new EnrollmentRejectedException(studentId, courseId, ErrorCode.NO_SEATS);
        }
    }
}