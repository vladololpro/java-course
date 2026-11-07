package java3;

import java3.task08.MaxUtils;
import java3.task08.Score;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class Task08Test {

    @Test
    void max_worksForIntegers() {
        assertEquals(9, MaxUtils.max(List.of(3, 7, 1, 9, 4)));
    }

    @Test
    void max_worksForStrings() {
        assertEquals("cherry", MaxUtils.max(List.of("banana", "apple", "cherry")));
    }

    @Test
    void max_worksForComparableValueObject() {
        Score max = MaxUtils.max(List.of(new Score(50), new Score(80), new Score(65)));

        assertEquals(80, max.points());
    }

    @Test
    void max_rejectsEmptyList() {
        IllegalArgumentException error = assertThrows(
            IllegalArgumentException.class,
            () -> MaxUtils.max(List.<Integer>of())
        );

        assertEquals("list is empty", error.getMessage());
    }
}
