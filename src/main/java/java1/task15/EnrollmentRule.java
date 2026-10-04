package java1.task15;

/* Открытый контракт правила. Сервис работает только с ним. */
public interface EnrollmentRule {
    RuleResult check(long studentId, int studentAge, long courseId);
}