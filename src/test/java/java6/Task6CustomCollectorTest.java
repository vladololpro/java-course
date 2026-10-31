package java6;

import java6.domain.Course;
import java6.domain.DurationStats;
import org.junit.jupiter.api.Test;

import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Task6CustomCollectorTest {

    @Test
    void collectsCountSumMinAndMax() {
        List<Course> courses = List.of(
                new Course(1, "JAVA-1", "A", 32, List.of()),
                new Course(2, "JAVA-2", "B", 40, List.of()),
                new Course(3, "SQL-1", "A", 20, List.of())
        );

        DurationStats stats = courses.stream().collect(CustomCollectorDemo.statsCollector());

        assertEquals(new DurationStats(3, 92, 20, 40), stats);
    }

    @Test
    void matchesSummarizingInt() {
        List<Course> courses = List.of(
                new Course(1, "JAVA-1", "A", 32, List.of()),
                new Course(2, "JAVA-2", "B", 40, List.of()),
                new Course(3, "SQL-1", "A", 20, List.of()),
                new Course(4, "WEB-1", "B", 12, List.of())
        );

        DurationStats stats = courses.stream().collect(CustomCollectorDemo.statsCollector());
        IntSummaryStatistics standard = courses.stream()
                .collect(Collectors.summarizingInt(Course::duration));

        assertEquals(standard.getCount(), stats.count());
        assertEquals(standard.getSum(), stats.sum());
        assertEquals(standard.getMin(), stats.min());
        assertEquals(standard.getMax(), stats.max());
    }

    @Test
    void combinerMergesParallelPartitions() {
        List<Course> courses = IntStream.rangeClosed(1, 1000)
                .mapToObj(i -> new Course(i, "C-" + i, "A", i, List.of()))
                .toList();

        DurationStats stats = courses.parallelStream().collect(CustomCollectorDemo.statsCollector());

        assertEquals(new DurationStats(1000, 500500, 1, 1000), stats);
    }

    @Test
    void emptyStreamReturnsEmptyStats() {
        DurationStats stats = Stream.<Course>empty().collect(CustomCollectorDemo.statsCollector());

        assertEquals(new DurationStats(0, 0, Integer.MAX_VALUE, Integer.MIN_VALUE), stats);
    }
}
