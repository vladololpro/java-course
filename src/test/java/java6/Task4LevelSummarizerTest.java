package java6;

import java6.domain.Course;
import java6.domain.LevelSummary;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Task4LevelSummarizerTest {

    private final LevelSummarizer summarizer = new LevelSummarizer();

    @Test
    void groupsByLevelAndCalculatesCountAndAverage() {
        List<Course> courses = List.of(
                new Course(1, "JAVA-1", "A", 32, List.of()),
                new Course(2, "JAVA-2", "B", 40, List.of()),
                new Course(3, "SQL-1", "A", 20, List.of()),
                new Course(4, "WEB-1", "B", 20, List.of())
        );

        Map<String, LevelSummary> summary = summarizer.summarize(courses);

        assertEquals(Map.of(
                "A", new LevelSummary(2, 26.0),
                "B", new LevelSummary(2, 30.0)
        ), summary);
    }

    @Test
    void returnsEmptySummaryForEmptyList() {
        assertTrue(summarizer.summarize(List.of()).isEmpty());
    }
}
