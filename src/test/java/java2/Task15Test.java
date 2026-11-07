package java2;

import java2.domain.CourseImporter;
import java2.domain.CourseStatus;
import java2.domain.EnrollmentService;
import java2.errors.CourseImportException;
import java2.errors.EnrollmentRejectedException;
import java2.errors.ErrorCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Task15Test {

    private final EnrollmentService service = new EnrollmentService();

    static class CloseFailingReader extends StringReader {
        CloseFailingReader(String s) { super(s); }
        @Override
        public void close() {
            throw new RuntimeException("Close failed!");
        }
    }

    @Test
    void enroll_withValidData_shouldNotThrow() {
        assertDoesNotThrow(() -> service.enroll(1L, 10L, CourseStatus.OPEN, 5, true, false));
    }

    @Test
    void enroll_withInvalidStudentId_shouldBeProgrammingError() {
        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,
            () -> service.enroll(0L, 10L, CourseStatus.OPEN, 5, true, false)
        );

        assertTrue(ex.getMessage().contains(ErrorCode.INVALID_STUDENT_OR_COURSE));
    }

    @Test
    void enroll_withInvalidCourseId_shouldBeProgrammingError() {
        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,
            () -> service.enroll(1L, 0L, CourseStatus.OPEN, 5, true, false)
        );

        assertTrue(ex.getMessage().contains(ErrorCode.INVALID_STUDENT_OR_COURSE));
    }

    @ParameterizedTest(name = "status={0}, seats={1}, prereq={2}, enrolled={3} -> {4}")
    @CsvSource({
        "OPEN,    0, true,  false, NO_SEATS",
        "CLOSED,  5, true,  false, COURSE_CLOSED",
        "ARCHIVED,5, true,  false, COURSE_ARCHIVED",
        "OPEN,    5, false, false, PREREQUISITE_MISSING",
        "OPEN,    5, true,  true,  ALREADY_ENROLLED",
        "OPEN,    5, false, true,  ALREADY_ENROLLED",
        "OPEN,    0, false, false, PREREQUISITE_MISSING",
        "CLOSED,  0, false, true,  COURSE_CLOSED",
        "ARCHIVED,0, false, true,  COURSE_ARCHIVED"
    })
    void enroll_withBusinessRuleViolation_shouldReturnStableCode(
            String status, int seats, boolean prereq, boolean enrolled, String expectedCode) {
        CourseStatus courseStatus = CourseStatus.valueOf(status);

        EnrollmentRejectedException ex = assertThrows(
            EnrollmentRejectedException.class,
            () -> service.enroll(1L, 10L, courseStatus, seats, prereq, enrolled)
        );

        assertEquals(expectedCode, ex.getReasonCode());
    }

    @ParameterizedTest(name = "status={0}, seats={1}, prereq={2}, enrolled={3}")
    @CsvSource({
        "OPEN, 1,   true, false",
        "OPEN, 100, true, false"
    })
    void enroll_withAllValidCombinations_shouldAllow(
            String status, int seats, boolean prereq, boolean enrolled) {
        CourseStatus courseStatus = CourseStatus.valueOf(status);

        assertDoesNotThrow(() -> service.enroll(1L, 10L, courseStatus, seats, prereq, enrolled));
    }

    @Test
    void enroll_rejection_shouldExposeStudentCourseAndCode() {
        EnrollmentRejectedException ex = assertThrows(
            EnrollmentRejectedException.class,
            () -> service.enroll(7L, 42L, CourseStatus.OPEN, 0, true, false)
        );

        assertEquals(7L, ex.getStudentId());
        assertEquals(42L, ex.getCourseId());
        assertEquals(ErrorCode.NO_SEATS, ex.getReasonCode());
        assertTrue(ex.getMessage().contains(ErrorCode.NO_SEATS));
    }

    @Test
    void import_withValidRecords_shouldReturnThem() throws CourseImportException {
        CourseImporter importer = new CourseImporter();

        List<String> result = importer.importCourses(new StringReader("JAVA-101: Java\nCS-999: Algorithms"));

        assertEquals(List.of("JAVA-101: Java", "CS-999: Algorithms"), result);
    }

    @ParameterizedTest(name = "malformed line: {0}")
    @ValueSource(strings = {"BAD_LINE", "NO_COLON", "  "})
    void import_withMalformedLine_shouldReturnStableImportCode(String line) {
        CourseImporter importer = new CourseImporter();

        CourseImportException ex = assertThrows(
            CourseImportException.class,
            () -> importer.importCourses(new StringReader(line))
        );

        assertEquals(ErrorCode.IMPORT_MALFORMED_RECORD, ex.getErrorCode());
        assertEquals(1, ex.getRecordNumber());
        assertEquals(0, ex.getProcessedRecords());
    }

    @Test
    void import_withIoReadError_shouldPreserveCause() {
        CourseImporter importer = new CourseImporter();
        Reader broken = new Reader() {
            @Override
            public int read(char[] cbuf, int off, int len) throws IOException {
                throw new IOException("read failed");
            }

            @Override
            public void close() {
            }
        };

        CourseImportException ex = assertThrows(
            CourseImportException.class,
            () -> importer.importCourses(broken)
        );

        assertEquals(ErrorCode.IMPORT_IO_ERROR, ex.getErrorCode());
        assertNotNull(ex.getCause());
        assertEquals("read failed", ex.getCause().getMessage());
    }

    @Test
    void import_withMalformedRecordAndCloseError_shouldSuppressCloseError() {
        CourseImporter importer = new CourseImporter();
        Reader reader = new CloseFailingReader("BAD_LINE");

        CourseImportException ex = assertThrows(
            CourseImportException.class,
            () -> importer.importCourses(reader)
        );

        assertEquals(ErrorCode.IMPORT_MALFORMED_RECORD, ex.getErrorCode());
        assertEquals(1, ex.getSuppressed().length);
        assertTrue(ex.getSuppressed()[0].getMessage().contains("Close failed!"));
    }

    @Test
    void import_afterFailure_shouldWorkAgain() {
        CourseImporter importer = new CourseImporter();

        assertThrows(
            CourseImportException.class,
            () -> importer.importCourses(new StringReader("BAD_LINE"))
        );

        assertDoesNotThrow(() -> importer.importCourses(new StringReader("JAVA-101: Java")));
    }
}
