package java6;

import java6.CourseHubReport.CoursePopularity;
import java6.CourseHubReport.Report;
import java6.domain.EnrollmentEvent;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Task7CourseHubReportTest {

    private final CourseHubReport courseHubReport = new CourseHubReport();

    @Test
    void duplicateEnrollmentIsNotCountedInCompletionRate() {
        List<EnrollmentEvent> events = List.of(
                new EnrollmentEvent(1, "JAVA-1", "A", true),
                new EnrollmentEvent(1, "JAVA-1", "A", true),
                new EnrollmentEvent(2, "JAVA-1", "A", false)
        );

        Report report = courseHubReport.buildReport(events);

        assertEquals(2, report.uniqueStudents());
        assertEquals(0.5, report.completionRate().get("JAVA-1"), 0.000001);
        assertEquals(List.of(new CoursePopularity("JAVA-1", 2)), report.topCourses());
        assertEquals(2L, report.levelDistribution().get("A"));
        assertEquals(1, report.anomalies().size());
    }

    @Test
    void topCoursesUseCourseCodeAsTieBreaker() {
        List<EnrollmentEvent> events = List.of(
                new EnrollmentEvent(1, "JAVA-2", "B", true),
                new EnrollmentEvent(2, "JAVA-1", "A", true),
                new EnrollmentEvent(3, "SQL-1", "A", false)
        );

        Report report = courseHubReport.buildReport(events);

        assertEquals(List.of(
                new CoursePopularity("JAVA-1", 1),
                new CoursePopularity("JAVA-2", 1),
                new CoursePopularity("SQL-1", 1)
        ), report.topCourses());
    }

    @Test
    void reportIsDeterministicForDifferentInputOrders() {
        List<EnrollmentEvent> firstOrder = List.of(
                new EnrollmentEvent(1, "JAVA-1", "A", true),
                new EnrollmentEvent(2, "JAVA-2", "B", false),
                new EnrollmentEvent(3, "SQL-1", "A", false)
        );

        List<EnrollmentEvent> secondOrder = List.of(
                new EnrollmentEvent(3, "SQL-1", "A", false),
                new EnrollmentEvent(2, "JAVA-2", "B", false),
                new EnrollmentEvent(1, "JAVA-1", "A", true)
        );

        Report first = courseHubReport.buildReport(firstOrder);
        Report second = courseHubReport.buildReport(secondOrder);

        assertEquals(first.completionRate(), second.completionRate());
        assertEquals(first.topCourses(), second.topCourses());
        assertEquals(first.levelDistribution(), second.levelDistribution());
    }
}
