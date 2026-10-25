package java2.errors;

/* Собственное исключение для отказа в зачислении. */
public class EnrollmentRejectedException extends RuntimeException {
    private final long studentId;
    private final long courseId;
    private final String reasonCode;

    public EnrollmentRejectedException(long studentId, long courseId, String reasonCode) {
        super("Enrollment rejected. Reason: " + reasonCode);
        this.studentId = studentId;
        this.courseId = courseId;
        this.reasonCode = reasonCode;
    }

    public EnrollmentRejectedException(long studentId, long courseId, String reasonCode, Throwable cause) {
        super("Enrollment rejected. Reason: " + reasonCode, cause);
        this.studentId = studentId;
        this.courseId = courseId;
        this.reasonCode = reasonCode;
    }

    public long getStudentId() { return studentId; }
    public long getCourseId() { return courseId; }
    public String getReasonCode() { return reasonCode; }
}