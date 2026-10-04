package java1.task15;

/* Демонстрационное правило, которое всегда не проходит. */
public class AlwaysFailRule implements EnrollmentRule {

    @Override
    public RuleResult check(long studentId, int studentAge, long courseId) {
        return new Failed("always fail");
    }
}