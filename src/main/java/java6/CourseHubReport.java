package java6;

import java6.domain.EnrollmentEvent;
import java.util.*;

/* Задание 7: Однопроходный отчет. */
public class CourseHubReport {

    public record StudentCourseKey(long studentId, String courseCode) {
    }

    public record CoursePopularity(String courseCode, long enrollments) {
    }

    public record Report(
            long uniqueStudents,
            Map<String, Double> completionRate,
            List<CoursePopularity> topCourses,
            Map<String, Long> levelDistribution,
            List<EnrollmentEvent> anomalies) {
    }

    public Report buildReport(List<EnrollmentEvent> events) {
        Set<StudentCourseKey> seen = new HashSet<>();
        Set<Long> uniqueStudents = new HashSet<>();
        Map<String, long[]> courseStats = new TreeMap<>();
        Map<String, Long> levelDistribution = new TreeMap<>();
        List<EnrollmentEvent> anomalies = new ArrayList<>();

        for (EnrollmentEvent event : events) {
            uniqueStudents.add(event.studentId());

            StudentCourseKey key = new StudentCourseKey(event.studentId(), event.courseCode());
            if (!seen.add(key)) {
                anomalies.add(event);
                continue;
            }

            long[] stats = courseStats.computeIfAbsent(event.courseCode(), code -> new long[2]);
            stats[0]++;
            if (event.completed()) {
                stats[1]++;
            }
            levelDistribution.merge(event.level(), 1L, Long::sum);
        }

        Map<String, Double> completionRate = new TreeMap<>();
        for (Map.Entry<String, long[]> entry : courseStats.entrySet()) {
            long[] stats = entry.getValue();
            completionRate.put(entry.getKey(), (double) stats[1] / stats[0]);
        }

        List<Map.Entry<String, long[]>> ranked = new ArrayList<>(courseStats.entrySet());
        ranked.sort(Comparator
                .comparingLong((Map.Entry<String, long[]> entry) -> entry.getValue()[0])
                .reversed()
                .thenComparing(Map.Entry.comparingByKey()));

        List<CoursePopularity> topCourses = new ArrayList<>();
        for (int i = 0; i < Math.min(3, ranked.size()); i++) {
            Map.Entry<String, long[]> entry = ranked.get(i);
            topCourses.add(new CoursePopularity(entry.getKey(), entry.getValue()[0]));
        }

        return new Report(
                uniqueStudents.size(),
                Collections.unmodifiableMap(completionRate),
                List.copyOf(topCourses),
                Collections.unmodifiableMap(levelDistribution),
                List.copyOf(anomalies)
        );
    }

    public void demo() {
        List<EnrollmentEvent> events = List.of(
            new EnrollmentEvent(1, "JAVA-1", "A", true),
            new EnrollmentEvent(1, "JAVA-1", "A", true),
            new EnrollmentEvent(2, "JAVA-1", "A", false),
            new EnrollmentEvent(3, "JAVA-2", "B", true),
            new EnrollmentEvent(4, "JAVA-2", "B", true),
            new EnrollmentEvent(5, "SQL-1", "A", false)
        );

        Report report = buildReport(events);

        System.out.println("Уникальных студентов: " + report.uniqueStudents());

        System.out.println("Completion rate (по уникальным записям):");
        report.completionRate().forEach((course, rate) ->
                System.out.printf("  %s: %.0f%%%n", course, rate * 100));

        System.out.println("Top-3 курсов по популярности:");
        report.topCourses().forEach(course ->
                System.out.println("  " + course.courseCode() + ": " + course.enrollments()));

        System.out.println("Распределение по level:");
        report.levelDistribution().forEach((level, count) ->
                System.out.println("  " + level + ": " + count));

        System.out.println("Аномалии (дубликаты):");
        report.anomalies().forEach(event ->
                System.out.println("  Студент " + event.studentId() + " уже записан на " + event.courseCode()));
    }
}
