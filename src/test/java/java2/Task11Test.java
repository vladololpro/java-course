package java2;

import java2.domain.CourseStatus;
import java2.domain.EnrollmentService;
import java2.errors.EnrollmentRejectedException;
import model.Course;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Task11Test {

    @Test
    void completeHours_aboveRemaining_shouldThrowWithMessageAndKeepState() {
        Course course = new Course(1, "Java", 32);
        course.completeHours(10);

        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,
            () -> course.completeHours(100)
        );

        assertTrue(ex.getMessage().contains("remaining: 22"), "Сообщение должно объяснять причину отказа");
        assertEquals(10, course.completedHours(), "Прогресс не должен измениться при ошибке");
    }

    @Test
    void completeHours_withNegativeAmount_shouldThrowAndKeepState() {
        Course course = new Course(1, "Java", 32);

        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,
            () -> course.completeHours(-1)
        );

        assertTrue(ex.getMessage().contains("negative"), "Сообщение должно объяснять причину отказа");
        assertEquals(0, course.completedHours(), "Прогресс не должен измениться при ошибке");
    }

    @Test
    void enroll_whenNoSeats_shouldExposeFieldsAndMessage() {
        EnrollmentService service = new EnrollmentService();

        EnrollmentRejectedException ex = assertThrows(
            EnrollmentRejectedException.class,
            () -> service.enroll(1L, 10L, CourseStatus.OPEN, 0, true, false)
        );

        assertEquals(1L, ex.getStudentId());
        assertEquals(10L, ex.getCourseId());
        assertEquals("NO_SEATS", ex.getReasonCode());
        assertTrue(ex.getMessage().contains("NO_SEATS"));
    }
}