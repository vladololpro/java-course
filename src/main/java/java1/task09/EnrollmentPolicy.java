package java1.task09;

import model.Course;

/* Контракт политики допуска: можно ли студенту записаться на курс. */
public interface EnrollmentPolicy {

    boolean canEnroll(Student student, Course course);
}