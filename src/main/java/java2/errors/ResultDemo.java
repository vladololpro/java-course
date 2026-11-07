package java2.errors;

import java.util.Optional;

import java2.domain.CourseCode;

public class ResultDemo {

    public void run() {
        System.out.println("1. findCourse (Optional - отсутствие это норма):");
        System.out.println("  Найден: " + findCourse(10L).map(c -> "Course " + c).orElse("Не найден"));
        System.out.println("  Не найден: " + findCourse(0L).map(c -> "Course " + c).orElse("Не найден"));

        System.out.println("\n2. parseCourse (Optional - неверный ввод это норма):");
        System.out.println("  Корректный: " + parseCourse("java-101").map(CourseCode::value).orElse("Неверный код"));
        System.out.println("  Неверный: " + parseCourse("JAVA101").map(CourseCode::value).orElse("Неверный код"));

        System.out.println("\n3. enroll (результат-значение - отказ это бизнес-результат):");
        printEnrollment(enroll(false, true, 5));
        printEnrollment(enroll(false, false, 5));

        System.out.println("\n4. calculatePrice (Unchecked - баг в коде):");
        System.out.println("  Корректная цена: " + calculatePrice(100));
        try {
            calculatePrice(-100);
        } catch (IllegalArgumentException e) {
            System.out.println("  Ошибка: " + e.getMessage());
        }
    }

    private Optional<String> findCourse(long id) {
        if (id > 0) return Optional.of("Java");
        return Optional.empty();
    }

    private Optional<CourseCode> parseCourse(String raw) {
        if (!CourseCode.isValid(raw)) return Optional.empty();
        return Optional.of(CourseCode.parse(raw));
    }

    private EnrollmentResult enroll(boolean alreadyEnrolled, boolean hasPrerequisite, int availableSeats) {
        if (alreadyEnrolled) return new EnrollmentResult(false, "ALREADY_ENROLLED");
        if (!hasPrerequisite) return new EnrollmentResult(false, "PREREQUISITE_MISSING");
        if (availableSeats <= 0) return new EnrollmentResult(false, "NO_SEATS");
        return new EnrollmentResult(true, null);
    }

    private void printEnrollment(EnrollmentResult result) {
        System.out.println("  accepted=" + result.accepted() + ", reason=" + result.reasonCode());
    }

    private int calculatePrice(int price) {
        if (price < 0) {
            throw new IllegalArgumentException("Цена не может быть отрицательной: " + price);
        }
        return price;
    }

    private record EnrollmentResult(boolean accepted, String reasonCode) {
    }
}