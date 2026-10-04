package java1.task15;

/* Правило: studentId должен быть из разрешённого диапазона. */
public class DomainRule implements EnrollmentRule {

    @Override
    public RuleResult check(long studentId, int studentAge, long courseId) {
        if (studentId > 0) {
            return new Passed("studentId " + studentId + " valid");
        }
        return new Failed("invalid studentId " + studentId);
    }
}