package java2.errors;

/* Демонстрация собственного исключения. */
public class OwnExceptionDemo {
    public void run() {
        try {
            enroll(1L, 10L, false);
        } catch (EnrollmentRejectedException e) {
            System.out.println("Сообщение: " + e.getMessage());
            System.out.println("Student: " + e.getStudentId());
            System.out.println("Course: " + e.getCourseId());
            System.out.println("Reason: " + e.getReasonCode());
        }

        System.out.println();
        try {
            enroll(2L, 10L, true);
        } catch (EnrollmentRejectedException e) {
            System.out.println("Сообщение: " + e.getMessage());
            System.out.println("Cause: " + e.getCause().getMessage());
        }
    }

    private void enroll(long studentId, long courseId, boolean simulateCause) {
        if (simulateCause) {
            throw new EnrollmentRejectedException(studentId, courseId, "PREREQUISITE_MISSING", 
                new IllegalStateException("Database is down"));
        }
        throw new EnrollmentRejectedException(studentId, courseId, "CAPACITY_FULL");
    }
}