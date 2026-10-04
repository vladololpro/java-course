package java1.task15;

/* Сервис зачисления. Принимает одно правило и не знает, какие конкретно правила внутри. */
public class EnrollmentService {

    private final EnrollmentRule rule;

    public EnrollmentService(EnrollmentRule rule) {
        this.rule = rule;
    }

    public boolean enroll(long studentId, int studentAge, long courseId) {
        RuleResult result = rule.check(studentId, studentAge, courseId);

        String description = switch (result) {
            case Passed p -> "OK: " + p.reason();
            case Failed f -> "NO: " + f.reason();
        };

        System.out.println("Student " + studentId + " → course " + courseId
            + " | " + description);

        return result.isPassed();
    }
}