package java3.task01;

/* Простая модель регистрации: id студента и id курса. */
public class Registration {

    private final long studentId;
    private final long courseId;

    public Registration(long studentId, long courseId) {
        this.studentId = studentId;
        this.courseId = courseId;
    }

    public long studentId() { return studentId; }
    public long courseId()  { return courseId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Registration r)) return false;
        return r.studentId == studentId && r.courseId == courseId;
    }

    @Override
    public String toString() {
        return "Reg(student=" + studentId + ", course=" + courseId + ")";
    }
}