package java6;

import java6.domain.Course;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Task3DurationReducerTest {

    private final DurationReducer reducer = new DurationReducer();

    @Test
    void sumsDurationsWithNeutralIdentity() {
        assertEquals(92, reducer.sumDurations(courses()));
    }

    @Test
    void joinsCourseCodesWithNeutralIdentity() {
        assertEquals("JAVA-1, JAVA-2, SQL-1", reducer.joinCourseCodes(courses()));
    }

    @Test
    void wrongSumIdentityDistortsResult() {
        int wrongSum = courses().stream()
                .map(Course::duration)
                .reduce(1000, Integer::sum);

        assertEquals(1092, wrongSum);
    }

    @Test
    void wrongStringIdentityAddsExtraElement() {
        String wrongCodes = courses().stream()
                .map(Course::code)
                .reduce("JAVA-1", DurationReducer::joinCodes);

        assertEquals("JAVA-1, JAVA-1, JAVA-2, SQL-1", wrongCodes);
    }

    @Test
    void joinCodesAccumulatorIsAssociative() {
        String left = DurationReducer.joinCodes(
                DurationReducer.joinCodes("JAVA-1", "JAVA-2"), "SQL-1");
        String right = DurationReducer.joinCodes(
                "JAVA-1", DurationReducer.joinCodes("JAVA-2", "SQL-1"));

        assertTrue(left.equals(right));
    }

    private List<Course> courses() {
        return List.of(
                new Course(1, "JAVA-1", "A", 32, List.of()),
                new Course(2, "JAVA-2", "B", 40, List.of()),
                new Course(3, "SQL-1", "A", 20, List.of())
        );
    }
}
