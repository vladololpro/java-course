package java1.task12;

/* В листе ожидания. Позиция в очереди. */
public final class WaitListed implements EnrollmentResult {

    private final long studentId;
    private final long courseId;
    private final int position;

    public WaitListed(long studentId, long courseId, int position) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.position = position;
    }

    public long studentId() { return studentId; }
    public long courseId() { return courseId; }
    public int position() { return position; }
}