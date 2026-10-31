package java6;

import java6.domain.Course;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

/* Задание 2: Использование flatMap для создания обратного индекса. */
public class TopicIndexer {

    public Map<String, List<Course>> buildTopicIndex(List<Course> courses) {
        return courses.stream()
                .flatMap(course -> course.modules().stream()
                        .map(topic -> Map.entry(normalize(topic), course)))
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey,
                        Collectors.mapping(Map.Entry::getValue, Collectors.toList())
                ));
    }

    private String normalize(String topic) {
        return topic.trim().toLowerCase(Locale.ROOT);
    }

    public void demo() {
        List<Course> courses = List.of(
            new Course(1, "JAVA-1", "A", 32, List.of("Java", "OOP")),
            new Course(2, "JAVA-2", "B", 40, List.of(" java ", "Streams")),
            new Course(3, "SQL-1", "A", 20, List.of("Databases", "SQL"))
        );

        Map<String, List<Course>> index = buildTopicIndex(courses);

        System.out.println("Обратный индекс тем:");
        index.forEach((topic, courseList) -> {
            System.out.println("Тема: '" + topic + "'");
            courseList.forEach(course -> System.out.println("  - " + course.code()));
        });
    }
}
