package java6;

import java6.domain.EnrollmentEvent;
import java.util.*;
import java.util.stream.Collectors;

/* Задание 7: Однопроходный отчет. */
public class CourseHubReport {
    public void demo() {
        List<EnrollmentEvent> events = List.of(
            new EnrollmentEvent(1, "JAVA-1", "A", true),
            new EnrollmentEvent(1, "JAVA-1", "A", true),
            new EnrollmentEvent(2, "JAVA-1", "A", false),
            new EnrollmentEvent(3, "JAVA-2", "B", true),
            new EnrollmentEvent(4, "JAVA-2", "B", true),
            new EnrollmentEvent(5, "SQL-1", "A", false)
        );

        long uniqueStudents = events.stream()
                .map(EnrollmentEvent::studentId)
                .distinct()
                .count();
        System.out.println("Уникальных студентов: " + uniqueStudents);

        Map<String, Double> completionRate = events.stream()
                .collect(Collectors.groupingBy(
                        EnrollmentEvent::courseCode,
                        Collectors.averagingInt(e -> e.completed() ? 1 : 0)
                ));
        System.out.println("Completion rate:");
        completionRate.forEach((course, rate) -> 
            System.out.printf("  %s: %.0f%%%n", course, rate * 100));

        System.out.println("Top-3 курсов по популярности:");
        events.stream()
                .collect(Collectors.groupingBy(EnrollmentEvent::courseCode, Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(3)
                .forEach(entry -> System.out.println("  " + entry.getKey() + ": " + entry.getValue()));

        Set<String> seen = new HashSet<>();
        List<EnrollmentEvent> anomalies = events.stream()
                .filter(e -> !seen.add(e.studentId() + ":" + e.courseCode()))
                .toList();
        System.out.println("Аномалии (дубликаты):");
        anomalies.forEach(a -> System.out.println("  Студент " + a.studentId() + " уже записан на " + a.courseCode()));
    }
}