package java1.task09;

/* Минимальная модель студента.
 * Пройденные курсы храним в массиве. */
public class Student {

    private final long id;
    private final int age;
    private final long[] completedCourseIds = new long[10];
    private int completedCount = 0;

    public Student(long id, int age) {
        this.id = id;
        this.age = age;
    }

    public long id() {
        return id;
    }

    public int age() {
        return age;
    }

    public void completeCourse(long courseId) {
        completedCourseIds[completedCount++] = courseId;
    }

    public boolean hasCompleted(long courseId) {
        for (int i = 0; i < completedCount; i++) {
            if (completedCourseIds[i] == courseId) {
                return true;
            }
        }
        return false;
    }
}