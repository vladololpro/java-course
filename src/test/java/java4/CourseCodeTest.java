package java4;

import org.junit.jupiter.api.Test;
import java.util.regex.Pattern;
import static org.junit.jupiter.api.Assertions.*;

class CourseCodeTest {

    private static final Pattern PATTERN = Pattern.compile("([A-Z]{2,4})-(\\d{3})");

    @Test
    void testValidCodes() {
        assertTrue(PATTERN.matcher("JAVA-101").matches());
        assertTrue(PATTERN.matcher("CS-999").matches());
    }

    @Test
    void testInvalidCodes() {
        assertFalse(PATTERN.matcher("java-101").matches());
        assertFalse(PATTERN.matcher("JAVA101").matches());
        assertFalse(PATTERN.matcher("JAVA-101!").matches());
    }
}