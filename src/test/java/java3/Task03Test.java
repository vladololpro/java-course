package java3;

import java3.task03.CourseIndex;
import model.Course;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Task03Test {

    @Test
    void repeatedId_isNotSilentlyOverwritten() {
        CourseIndex index = new CourseIndex();
        Course java = new Course(1, "Java", 32);

        assertTrue(index.addSafely(java));
        assertFalse(index.addSafely(new Course(1, "Other", 30)));

        assertEquals("Java", index.get(1).title());
        assertEquals(1, index.size());
    }

    @Test
    void getAndContainsKeyWorkForExistingAndMissingIds() {
        CourseIndex index = new CourseIndex();
        index.addSafely(new Course(1, "Java", 32));

        assertTrue(index.contains(1));
        assertFalse(index.contains(2));
        assertNull(index.get(2));
    }

    @Test
    void putIfAbsentKeepsExistingValue() {
        CourseIndex index = new CourseIndex();
        Course algorithms = new Course(2, "Algorithms", 40);

        assertNull(index.putIfAbsent(2, algorithms));
        assertEquals(algorithms, index.putIfAbsent(2, new Course(2, "Other", 30)));
        assertEquals("Algorithms", index.get(2).title());
    }

    @Test
    void computeIfAbsentCreatesOnlyMissingValue() {
        CourseIndex index = new CourseIndex();
        Course java = index.computeIfAbsent(1, "Java");

        assertEquals("Java", java.title());
        assertEquals(java, index.computeIfAbsent(1, "Ignored"));

        Course databases = index.computeIfAbsent(2, "Databases");

        assertEquals("Databases", databases.title());
        assertEquals(2, index.size());
    }
}
