package java1.task15;

/* Правило прошло. */
public final class Passed implements RuleResult {

    private final String reason;

    public Passed(String reason) {
        this.reason = reason;
    }

    public String reason() {
        return reason;
    }

    @Override
    public boolean isPassed() {
        return true;
    }
}