package java1.task12;

/* Обработка результата через exhaustive switch.
 * Без default - компилятор сам проверит, что все случаи покрыты. */
public class ResultPrinter {

    public static String describe(EnrollmentResult result) {
        return switch (result) {
            case Accepted a ->
                "Student " + a.studentId() + " accepted to course " + a.courseId();
            case Rejected r ->
                "Student " + r.studentId() + " rejected from course "
                    + r.courseId() + ": " + r.reason();
            case WaitListed w ->
                "Student " + w.studentId() + " waitlisted for course "
                    + w.courseId() + ", position " + w.position();
        };
    }
}