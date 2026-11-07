package java2;

import java2.domain.CourseCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class Task10Test {

    @ParameterizedTest(name = "input={0}, expected={1}")
    @CsvSource({
        "JAVA-101, JAVA-101",
        "java-101, JAVA-101",
        "CS-999,   CS-999",
        "web-001,  WEB-001"
    })
    void parse_shouldNormalizeValidCode(String input, String expected) {
        assertEquals(expected, CourseCode.parse(input).value());
    }

    @Test
    void parse_shouldTrimSpaces() {
        assertEquals("JAVA-101", CourseCode.parse("  java-101  ").value());
    }

    @ParameterizedTest(name = "input={0}, expected=rejected")
    @ValueSource(strings = {"", " ", "JAVA_101", "101", "JAVA-10", "TOOLONG-101", "JAVA-101!"})
    void parse_shouldRejectInvalidCode(String input) {
        assertThrows(IllegalArgumentException.class, () -> CourseCode.parse(input));
    }

    @ParameterizedTest(name = "input=null, expected=rejected")
    @NullSource
    void parse_shouldRejectNullCode(String input) {
        assertThrows(IllegalArgumentException.class, () -> CourseCode.parse(input));
    }
}
