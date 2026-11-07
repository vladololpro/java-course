package java3;

import java3.task12.Repository;
import model.Course;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Task12Test {

    @Test
    void repository_savesAndFindsEntity() {
        Repository<Long, Course> repository = new Repository<>();
        Course java = new Course(1, "Java", 32);

        repository.save(1L, java);

        assertEquals("Java", repository.findById(1L).title());
    }

    @Test
    void repeatedSaveWithSameId_updatesValue() {
        Repository<Long, Course> repository = new Repository<>();
        repository.save(1L, new Course(1, "Java", 32));
        repository.save(1L, new Course(1, "Java Advanced", 50));

        assertEquals("Java Advanced", repository.findById(1L).title());
        assertEquals(1, repository.findAll().size());
    }

    @Test
    void findAll_returnsCopyOfInternalCollection() {
        Repository<Long, Course> repository = new Repository<>();
        repository.save(1L, new Course(1, "Java", 32));

        List<Course> all = repository.findAll();
        all.clear();

        assertEquals(1, repository.findAll().size());
    }

    @Test
    void deleteById_removesEntity() {
        Repository<Long, Course> repository = new Repository<>();
        repository.save(1L, new Course(1, "Java", 32));

        repository.deleteById(1L);

        assertNull(repository.findById(1L));
        assertTrue(repository.findAll().isEmpty());
    }

    @Test
    void nullIdAndEntityAreRejected() {
        Repository<Long, Course> repository = new Repository<>();
        Course java = new Course(1, "Java", 32);

        assertThrows(IllegalArgumentException.class, () -> repository.save(null, java));
        assertThrows(IllegalArgumentException.class, () -> repository.save(1L, null));
        assertThrows(IllegalArgumentException.class, () -> repository.findById(null));
        assertThrows(IllegalArgumentException.class, () -> repository.deleteById(null));
    }
}
