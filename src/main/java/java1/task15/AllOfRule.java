package java1.task15;

import java.util.List;

/* Композитное правило: все внутренние правила должны пройти. */
public class AllOfRule implements EnrollmentRule {

    private final List<EnrollmentRule> rules;

    public AllOfRule(List<EnrollmentRule> rules) {
        this.rules = rules;
    }

    @Override
    public RuleResult check(long studentId, int studentAge, long courseId) {
        for (EnrollmentRule rule : rules) {
            RuleResult result = rule.check(studentId, studentAge, courseId);
            if (!result.isPassed()) {
                return result;
            }
        }
        return new Passed("all " + rules.size() + " rules passed");
    }
}