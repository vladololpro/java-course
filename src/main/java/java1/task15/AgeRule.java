package java1.task15;

/* Правило: возраст не меньше minAge. */
public class AgeRule implements EnrollmentRule {

    private final int minAge;

    public AgeRule(int minAge) {
        this.minAge = minAge;
    }

    @Override
    public RuleResult check(long studentId, int studentAge, long courseId) {
        if (studentAge >= minAge) {
            return new Passed("age " + studentAge + " >= " + minAge);
        }
        return new Failed("age " + studentAge + " < " + minAge);
    }
}