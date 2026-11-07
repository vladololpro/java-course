package java3;

import java3.task07.ListUtils;
import model.Course;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class Task07Test {

    @Test
    void firstOrThrow_returnsFirstElement() {
        assertEquals("first", ListUtils.firstOrThrow(List.of("first", "second")));
    }

    @Test
    void firstOrThrow_rejectsEmptyList() {
        IllegalArgumentException error = assertThrows(
            IllegalArgumentException.class,
            () -> ListUtils.firstOrThrow(List.of())
        );

        assertEquals("list is empty", error.getMessage());
    }

    @Test
    void swap_exchangesElements() {
        List<String> list = new ArrayList<>(List.of("A", "B", "C"));

        ListUtils.swap(list, 0, 2);

        assertEquals(List.of("C", "B", "A"), list);
    }

    @Test
    void indexBy_buildsIndexByKey() {
        Course java = new Course(1, "Java", 32);
        Course algorithms = new Course(2, "Algorithms", 40);

        Map<Long, Course> index = ListUtils.indexBy(
            List.of(java, algorithms),
            Course::id
        );

        assertEquals(2, index.size());
        assertEquals("Algorithms", index.get(2L).title());
    }

    @Test
    void indexBy_rejectsDuplicateKeys() {
        List<Course> courses = List.of(
            new Course(1, "Java", 32),
            new Course(1, "Java Advanced", 40)
        );

        IllegalArgumentException error = assertThrows(
            IllegalArgumentException.class,
            () -> ListUtils.indexBy(courses, Course::id)
        );

        assertEquals("duplicate key: 1", error.getMessage());
    }
}
