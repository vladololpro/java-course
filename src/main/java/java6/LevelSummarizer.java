package java6;

import java6.domain.Course;
import java6.domain.LevelSummary;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/* Задание 4: Collectors для группировки и агрегации. */
public class LevelSummarizer {

    public Map<String, LevelSummary> summarize(List<Course> courses) {
        return courses.stream()
                .collect(Collectors.groupingBy(
                        Course::level,
                        Collectors.teeing(
                                Collectors.counting(),
                                Collectors.averagingInt(Course::duration),
                                LevelSummary::new
                        )
                ));
    }

    public void demo() {
        List<Course> courses = List.of(
            new Course(1, "JAVA-1", "A", 32, List.of()),
            new Course(2, "JAVA-2", "B", 40, List.of()),
            new Course(3, "SQL-1", "A", 20, List.of()),
            new Course(4, "WEB-1", "B", 20, List.of())
        );

        Map<String, LevelSummary> summary = summarize(courses);
        
        System.out.println("Сводка по уровням:");
        summary.forEach((level, stats) -> 
            System.out.printf("  Level %s: count=%d, avgDuration=%.1f%n", 
                level, stats.count(), stats.averageDuration())
        );
    }
}