package java1.task15;

/* Правило не прошло. */
public final class Failed implements RuleResult {

    private final String reason;

    public Failed(String reason) {
        this.reason = reason;
    }

    public String reason() {
        return reason;
    }

    @Override
    public boolean isPassed() {
        return false;
    }
}