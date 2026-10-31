package java6;

import java6.domain.Course;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Task2TopicIndexerTest {

    private final TopicIndexer indexer = new TopicIndexer();

    @Test
    void buildsNormalizedTopicIndex() {
        Course java1 = new Course(1, "JAVA-1", "A", 32, List.of("Java", "OOP"));
        Course java2 = new Course(2, "JAVA-2", "B", 40, List.of(" java ", "Streams"));
        Course sql = new Course(3, "SQL-1", "A", 20, List.of("Databases", "SQL"));

        Map<String, List<Course>> index = indexer.buildTopicIndex(List.of(java1, java2, sql));

        assertEquals(Set.of("java", "oop", "streams", "databases", "sql"), index.keySet());
        assertEquals(List.of(java1, java2), index.get("java"));
        assertEquals(List.of(java1), index.get("oop"));
        assertEquals(List.of(java2), index.get("streams"));
        assertEquals(List.of(sql), index.get("databases"));
        assertEquals(List.of(sql), index.get("sql"));
    }

    @Test
    void returnsEmptyIndexForCoursesWithoutModules() {
        Course course = new Course(1, "JAVA-1", "A", 32, List.of());

        assertTrue(indexer.buildTopicIndex(List.of(course)).isEmpty());
    }
}
