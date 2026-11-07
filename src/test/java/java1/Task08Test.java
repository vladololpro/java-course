package java1;

import java1.task08.BrokenReadOnlyCourseRepository;
import java1.task08.CourseRepository;
import java1.task08.FullCourseRepository;
import java1.task08.InMemoryCourseRepository;
import java1.task08.ReadOnlyCourseRepository;
import model.Course;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Task08Test {

    @Test
    void readOnlyRepository_findsSeededCourse() {
        ReadOnlyCourseRepository repository = new ReadOnlyCourseRepository();
        repository.seed(new Course(1, "Java", 32));

        Course course = repository.findById(1).orElseThrow();

        assertEquals("Java", course.title());
    }

    @Test
    void readOnlyRepository_returnsEmptyForMissingId() {
        ReadOnlyCourseRepository repository = new ReadOnlyCourseRepository();

        assertFalse(repository.findById(99).isPresent());
    }

    @Test
    void fullRepository_supportsReadingAndWriting() {
        FullCourseRepository repository = new InMemoryCourseRepository();
        repository.save(new Course(2, "Patterns", 40));

        Course course = repository.findById(2).orElseThrow();

        assertEquals("Patterns", course.title());
    }

    @Test
    void brokenReadOnlyRepository_violatesLspOnSave() {
        CourseRepository repository = new BrokenReadOnlyCourseRepository();

        assertThrows(
            UnsupportedOperationException.class,
            () -> repository.save(new Course(1, "Java", 32))
        );
    }

    @Test
    void writableAndReadableContractsCanBeUsedTogether() {
        InMemoryCourseRepository repository = new InMemoryCourseRepository();
        repository.save(new Course(3, "Refactoring", 50));

        assertTrue(repository.findById(3).isPresent());
    }
}
