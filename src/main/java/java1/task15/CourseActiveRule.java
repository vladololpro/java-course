package java1.task15;

/* Правило: курс «активен» (id положительный). */
public class CourseActiveRule implements EnrollmentRule {

    @Override
    public RuleResult check(long studentId, int studentAge, long courseId) {
        if (courseId > 0) {
            return new Passed("course " + courseId + " active");
        }
        return new Failed("course " + courseId + " inactive");
    }
}