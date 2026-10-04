package java1.task15;

import java.util.List;

/* Композитное правило: хотя бы одно из внутренних должно пройти. */
public class AnyOfRule implements EnrollmentRule {

    private final List<EnrollmentRule> rules;

    public AnyOfRule(List<EnrollmentRule> rules) {
        this.rules = rules;
    }

    @Override
    public RuleResult check(long studentId, int studentAge, long courseId) {
        RuleResult lastFail = null;
        for (EnrollmentRule rule : rules) {
            RuleResult result = rule.check(studentId, studentAge, courseId);
            if (result.isPassed()) {
                return result;
            }
            lastFail = result;
        }
        return new Failed("no rule passed, last: "
            + (lastFail == null ? "n/a" : lastFail.reason()));
    }
}