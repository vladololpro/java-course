package java1.task09;

import model.Course;

/* Служба зачисления. Принимает политику конструктором.
 * Какой алгоритм проверки - не её дело. */
public class EnrollmentService {

    private final EnrollmentPolicy policy;

    public EnrollmentService(EnrollmentPolicy policy) {
        this.policy = policy;
    }

    public boolean enroll(Student student, Course course) {
        if (!policy.canEnroll(student, course)) {
            System.out.println("Rejected: student " + student.id()
                + " -> course " + course.title());
            return false;
        }
        System.out.println("Accepted: student " + student.id()
            + " -> course " + course.title());
        return true;
    }
}