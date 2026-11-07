package java2;

import model.Course;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Task09Test {

    @Test
    void durationHours_belowMinimum_shouldNotBeSet() {
        Course course = new Course(1, "Java", 0);

        assertEquals(0, course.durationHours(), "0 меньше минимума 1, значение не должно установиться");
    }

    @Test
    void durationHours_atMinimum_shouldBeAccepted() {
        Course course = new Course(1, "Java", 1);

        assertEquals(1, course.durationHours());
    }

    @Test
    void durationHours_atMaximum_shouldBeAccepted() {
        Course course = new Course(1, "Java", 300);

        assertEquals(300, course.durationHours());
    }

    @Test
    void durationHours_aboveMaximum_shouldNotBeSet() {
        Course course = new Course(1, "Java", 301);

        assertEquals(0, course.durationHours(), "301 больше максимума 300, значение не должно установиться");
    }
}