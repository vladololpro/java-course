package java3;

import java3.task06.Result;
import model.Course;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Task06Test {

    @Test
    void successResult_containsValueAndNoMessage() {
        Course course = new Course(1, "Java", 32);
        Result<Course> result = Result.success(course);

        assertTrue(result.isSuccess());
        assertSame(course, result.value());
        assertNull(result.message());
    }

    @Test
    void failureResult_containsMessageAndNoValue() {
        Result<Course> result = Result.failure("course not found");

        assertFalse(result.isSuccess());
        assertNull(result.value());
        assertEquals("course not found", result.message());
    }

    @Test
    void resultSupportsDifferentConcreteTypes() {
        Result<String> stringResult = Result.success("hello");
        Result<Integer> integerResult = Result.success(42);

        assertEquals("hello", stringResult.value());
        assertEquals(42, integerResult.value());
    }
}
