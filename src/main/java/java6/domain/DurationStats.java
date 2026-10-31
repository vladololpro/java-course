package java6.domain;

/* Неизменяемый объект статистики. */
public record DurationStats(long count, long sum, int min, int max) {
}