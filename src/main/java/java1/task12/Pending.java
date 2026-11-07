package java1.task12;

/* Новый permitted-тип: заявка ещё рассматривается.
 * Добавлен, чтобы показать, что компилятор заставляет обновить switch. */
public final class Pending implements EnrollmentResult {

    private final long studentId;
    private final long courseId;

    public Pending(long studentId, long courseId) {
        this.studentId = studentId;
        this.courseId = courseId;
    }

    public long studentId() { return studentId; }
    public long courseId() { return courseId; }
}
