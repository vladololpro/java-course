package java6;

import java6.domain.Course;
import java6.domain.DurationStats;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/* Задание 6: Собственный Collector. */
public class CustomCollectorDemo {
    public static Collector<Course, ?, DurationStats> statsCollector() {
        return Collector.of(
                () -> new int[]{0, 0, Integer.MAX_VALUE, Integer.MIN_VALUE},
                
                (acc, course) -> {
                    acc[0]++;
                    acc[1] += course.duration();
                    acc[2] = Math.min(acc[2], course.duration());
                    acc[3] = Math.max(acc[3], course.duration());
                },
                
                (acc1, acc2) -> new int[]{
                    acc1[0] + acc2[0],
                    acc1[1] + acc2[1],
                    Math.min(acc1[2], acc2[2]),
                    Math.max(acc1[3], acc2[3])
                },
                
                acc -> new DurationStats(acc[0], acc[1], acc[2], acc[3])
        );
    }

    public void demo() {
        List<Course> courses = List.of(
            new Course(1, "JAVA-1", "A", 32, List.of()),
            new Course(2, "JAVA-2", "B", 40, List.of()),
            new Course(3, "SQL-1", "A", 20, List.of())
        );

        System.out.println("1. Собственный коллектор:");
        DurationStats customStats = courses.stream().collect(statsCollector());
        System.out.println("  " + customStats);

        System.out.println("\n2. Готовый summarizingInt:");
        IntSummaryStatistics standardStats = courses.stream()
                .collect(Collectors.summarizingInt(Course::duration));
        System.out.println("  " + standardStats);
    }
}