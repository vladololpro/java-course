package java1.task09;

import model.Course;

/* Демонстрация паттерна «Стратегия»:
 * одна и та же служба с разными политиками ведёт себя по-разному. */
public class Main {

    public static void main(String[] args) {

        Course course = new Course(10, "Java Fundamentals", 32);

        Student young = new Student(1, 16);
        Student adult = new Student(2, 22);

        EnrollmentService ageService = new EnrollmentService(new AgePolicy(18));
        System.out.println("--- AgePolicy(18) ---");
        ageService.enroll(young, course);
        ageService.enroll(adult, course);
        System.out.println();

        EnrollmentService prereqService = new EnrollmentService(new PrerequisitePolicy(5));

        System.out.println("--- PrerequisitePolicy(5) ---");
        prereqService.enroll(adult, course);
        adult.completeCourse(5);
        prereqService.enroll(adult, course);
        System.out.println();

        CapacityPolicy capacity = new CapacityPolicy(1);
        EnrollmentService capService = new EnrollmentService(capacity);

        System.out.println("--- CapacityPolicy(1) ---");
        capService.enroll(adult, course);
        if (capService.enroll(young, course)) {
            capacity.enrollOne();
        }
    }
}