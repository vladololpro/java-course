package java6.domain;

/* Событие записи на курс. */
public record EnrollmentEvent(long studentId, String courseCode, String level, boolean completed) {
}
