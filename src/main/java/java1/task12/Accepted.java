package java1.task12;

/* Успешное зачисление. final - нельзя наследовать дальше. */
public final class Accepted implements EnrollmentResult {

    private final long studentId;
    private final long courseId;

    public Accepted(long studentId, long courseId) {
        this.studentId = studentId;
        this.courseId = courseId;
    }

    public long studentId() { return studentId; }
    public long courseId() { return courseId; }
}