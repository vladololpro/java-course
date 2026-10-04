package java1.task12;

/* Отказ. Причина - строка. */
public final class Rejected implements EnrollmentResult {

    private final long studentId;
    private final long courseId;
    private final String reason;

    public Rejected(long studentId, long courseId, String reason) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.reason = reason;
    }

    public long studentId() { return studentId; }
    public long courseId() { return courseId; }
    public String reason() { return reason; }
}