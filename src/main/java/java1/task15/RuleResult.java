package java1.task15;

/* Sealed-иерархия результата правила.
 * Только два варианта: Passed или Failed. */
public sealed interface RuleResult permits Passed, Failed {
    boolean isPassed();
    String reason();
}