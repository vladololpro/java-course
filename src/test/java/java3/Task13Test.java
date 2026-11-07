package java3;

import java3.task13.TagIndex;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Task13Test {

    @Test
    void indexFindsCoursesBySingleTag() {
        TagIndex index = new TagIndex();
        index.addCourse(1, Set.of("java", "backend"));
        index.addCourse(2, Set.of("java", "algorithms"));

        assertEquals(Set.of(1L, 2L), index.findByTags(Set.of("java")));
        assertEquals(Set.of(1L), index.findByTags(Set.of("backend")));
    }

    @Test
    void indexReturnsIntersectionForSeveralTags() {
        TagIndex index = new TagIndex();
        index.addCourse(1, Set.of("java", "backend"));
        index.addCourse(2, Set.of("java", "algorithms"));
        index.addCourse(3, Set.of("java", "backend", "databases"));

        assertEquals(Set.of(1L, 3L), index.findByTags(Set.of("java", "backend")));
        assertEquals(Set.of(3L), index.findByTags(Set.of("java", "backend", "databases")));
    }

    @Test
    void unknownTagReturnsEmptySet() {
        TagIndex index = new TagIndex();
        index.addCourse(1, Set.of("java"));

        assertTrue(index.findByTags(Set.of("java", "unknown")).isEmpty());
    }

    @Test
    void removeCourse_removesEmptyTagsFromIndex() {
        TagIndex index = new TagIndex();
        index.addCourse(1, Set.of("java", "backend"));
        index.addCourse(2, Set.of("java", "algorithms"));
        assertEquals(3, index.tagCount());

        index.removeCourse(1);

        assertEquals(Set.of(2L), index.findByTags(Set.of("java")));
        assertTrue(index.findByTags(Set.of("backend")).isEmpty());
        assertEquals(2, index.tagCount());
    }
}
