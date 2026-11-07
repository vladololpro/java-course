package java2;

import java2.domain.CourseImporter;
import java2.errors.CourseImportException;
import java2.errors.ErrorCode;
import org.junit.jupiter.api.Test;

import java.io.Reader;
import java.io.StringReader;
import java.util.List;

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
    void importCourses_allValid_shouldReturnAllRecords() throws CourseImportException {
        CourseImporter importer = new CourseImporter();
        Reader reader = new StringReader("GOOD1: OK\nGOOD2: OK");

        List<String> result = importer.importCourses(reader);

        assertEquals(List.of("GOOD1: OK", "GOOD2: OK"), result);
    }

    @Test
    void importCourses_malformedRecord_shouldKeepProcessedCountAndNotReturnPartial() {
        CourseImporter importer = new CourseImporter();
        Reader reader = new StringReader("GOOD1: OK\nGOOD2: OK\nBAD_LINE");

        CourseImportException ex = assertThrows(
            CourseImportException.class,
            () -> importer.importCourses(reader)
        );

        assertEquals(3, ex.getRecordNumber());
        assertEquals(2, ex.getProcessedRecords(), "До ошибки успешно обработаны две записи");
        assertEquals(ErrorCode.IMPORT_MALFORMED_RECORD, ex.getErrorCode());
        assertTrue(ex.getMessage().contains("Malformed record"));
    }

    @Test
    void importCourses_malformedRecordAndCloseError_shouldHaveSuppressedException() {
        CourseImporter importer = new CourseImporter();
        Reader reader = new FailingReader("BAD_LINE");

        CourseImportException ex = assertThrows(
            CourseImportException.class,
            () -> importer.importCourses(reader)
        );

        assertEquals(ErrorCode.IMPORT_MALFORMED_RECORD, ex.getErrorCode());

        Throwable[] suppressed = ex.getSuppressed();
        assertEquals(1, suppressed.length, "Должно быть подавленное исключение закрытия");
        assertTrue(suppressed[0].getMessage().contains("Close failed!"));
    }
}
