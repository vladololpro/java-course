package java2;

import java2.domain.CourseStatus;
import java2.domain.EnrollmentService;
import java2.errors.EnrollmentRejectedException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class Task15Test {

    private final EnrollmentService service = new EnrollmentService();

    @Test
    void enroll_nullId_shouldThrowUnchecked() {
        assertThrows(NullPointerException.class, 
            () -> service.enroll(0, 10L, CourseStatus.OPEN, 5, true, false));
    }

    @ParameterizedTest
    @CsvSource({
        "OPEN, 0, true, false, NO_SEATS",
        "CLOSED, 5, true, false, COURSE_CLOSED",
        "ARCHIVED, 5, true, false, COURSE_ARCHIVED",
        "OPEN, 5, false, false, PREREQUISITE_MISSING",
        "OPEN, 5, true, true, ALREADY_ENROLLED"
    })
    void enroll_businessErrors_shouldReturnStableCodes(String status, int seats, boolean prereq, boolean enrolled, String expectedCode) {
        CourseStatus courseStatus = CourseStatus.valueOf(status);
        EnrollmentRejectedException ex = assertThrows(EnrollmentRejectedException.class, 
            () -> service.enroll(1L, 10L, courseStatus, seats, prereq, enrolled));
        
        assertEquals(expectedCode, ex.getReasonCode());
    }

    @Test
    void enroll_validData_shouldNotThrow() {
        assertDoesNotThrow(() -> service.enroll(1L, 10L, CourseStatus.OPEN, 5, true, false));
    }
}