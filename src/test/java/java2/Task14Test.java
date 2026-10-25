package java2;

import java2.domain.CourseImporter;
import org.junit.jupiter.api.Test;

import java.io.Reader;
import java.io.StringReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class Task14Test {

    static class FailingReader extends StringReader {
        FailingReader(String s) { super(s); }
        @Override
        public void close() {
            throw new RuntimeException("Close failed!");
        }
    }

    @Test
    void importCourses_malformedRecord_shouldThrowAndNotReturnPartial() {
        CourseImporter importer = new CourseImporter();
        Reader reader = new StringReader("GOOD1: OK\nGOOD2: OK\nBAD_LINE");

        IOException ex = assertThrows(IOException.class, () -> importer.importCourses(reader));
        assertTrue(ex.getMessage().contains("Malformed record at line 3"));
    }

    @Test
    void importCourses_errorInReadAndClose_shouldHaveSuppressedException() {
        CourseImporter importer = new CourseImporter();
        Reader reader = new FailingReader("BAD_LINE");

        IOException ex = assertThrows(IOException.class, () -> importer.importCourses(reader));
        
        assertTrue(ex.getMessage().contains("Malformed record"));
        
        Throwable[] suppressed = ex.getSuppressed();
        assertEquals(1, suppressed.length, "Должно быть подавленное исключение");
        assertTrue(suppressed[0].getMessage().contains("Close failed!"));
    }
}