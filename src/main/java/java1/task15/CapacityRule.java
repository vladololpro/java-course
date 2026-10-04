package java1.task15;

/* Правило: на курсе есть свободные места. */
public class CapacityRule implements EnrollmentRule {

    private final int capacity;
    private int enrolled;

    public CapacityRule(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public RuleResult check(long studentId, int studentAge, long courseId) {
        if (enrolled < capacity) {
            return new Passed("seats " + enrolled + "/" + capacity);
        }
        return new Failed("no seats (" + enrolled + "/" + capacity + ")");
    }

    public void enrollOne() {
        enrolled++;
    }
}