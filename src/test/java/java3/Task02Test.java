package java3;

import java3.task02.CourseCode;
import java3.task02.CourseCodeWithoutEquals;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class Task02Test {

    @Test
    void courseCode_usesValueEqualityAndHashCode() {
        CourseCode first = new CourseCode("CS101");
        CourseCode second = new CourseCode("CS101");

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
        assertNotEquals(first, new CourseCode("MATH201"));
    }

    @Test
    void linkedHashSet_removesDuplicatesAndKeepsFirstAppearanceOrder() {
        List<CourseCode> input = List.of(
            new CourseCode("CS101"),
            new CourseCode("MATH201"),
            new CourseCode("CS101"),
            new CourseCode("PHYS100"),
            new CourseCode("MATH201")
        );

        Set<CourseCode> unique = new LinkedHashSet<>(input);

        assertEquals(
            List.of("CS101", "MATH201", "PHYS100"),
            unique.stream().map(CourseCode::value).toList()
        );
    }

    @Test
    void withoutEqualsAndHashCode_duplicatesRemainInHashSet() {
        Set<CourseCodeWithoutEquals> set = new HashSet<>(List.of(
            new CourseCodeWithoutEquals("CS101"),
            new CourseCodeWithoutEquals("CS101")
        ));

        assertEquals(2, set.size());
    }
}
