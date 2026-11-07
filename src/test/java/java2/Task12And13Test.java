package java2;

import java2.domain.CourseStatus;
import java2.domain.EnrollmentService;
import java2.errors.EnrollmentRejectedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.Random.class)
class Task12And13Test {

    private EnrollmentService service;

    @BeforeEach
    void setUp() {
        service = new EnrollmentService();
    }

    @ParameterizedTest(name = "status={0}, seats={1}, prereq={2}, enrolled={3} -> {4}")
    @CsvSource({
        "OPEN,    5, true,  false, ",
        "OPEN,    5, true,  true,  ALREADY_ENROLLED",
        "OPEN,    5, false, false, PREREQUISITE_MISSING",
        "OPEN,    5, false, true,  ALREADY_ENROLLED",
        "OPEN,    0, true,  false, NO_SEATS",
        "OPEN,    0, false, false, PREREQUISITE_MISSING",
        "OPEN,    0, false, true,  ALREADY_ENROLLED",
        "CLOSED,  5, true,  false, COURSE_CLOSED",
        "CLOSED,  0, false, true,  COURSE_CLOSED",
        "ARCHIVED,5, true,  false, COURSE_ARCHIVED",
        "ARCHIVED,0, false, true,  COURSE_ARCHIVED"
    })
    void enroll_shouldFollowDecisionTable(String status, int seats, boolean prereq, boolean enrolled, String expectedReason) {
        CourseStatus courseStatus = CourseStatus.valueOf(status);

        if (expectedReason == null || expectedReason.isEmpty()) {
            assertDoesNotThrow(() -> service.enroll(1L, 10L, courseStatus, seats, prereq, enrolled));
        } else {
            EnrollmentRejectedException ex = assertThrows(
                EnrollmentRejectedException.class,
                () -> service.enroll(1L, 10L, courseStatus, seats, prereq, enrolled)
            );
            assertEquals(expectedReason, ex.getReasonCode());
        }
    }

    @Test
    void enroll_withFreshService_shouldNotShareState() {
        EnrollmentService first = new EnrollmentService();
        first.enroll(1L, 10L, CourseStatus.OPEN, 1, true, false);

        EnrollmentService fresh = new EnrollmentService();

        assertDoesNotThrow(() -> fresh.enroll(2L, 10L, CourseStatus.OPEN, 1, true, false));
    }
}