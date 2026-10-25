package java2;

import model.Course;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Task09Test {

    @Test
    void durationHours_atMinimumBoundary_shouldBeValid() {
        Course c = new Course(1, "Java", 1);
        assertEquals(1, c.durationHours());
    }

    @Test
    void durationHours_atMaximumBoundary_shouldBeValid() {
        Course c = new Course(1, "Java", 100);
        assertEquals(100, c.durationHours());
    }

    @Test
    void durationHours_belowMinimum_shouldNotBeSet() {
        Course c = new Course(1, "Java", 0);
        assertEquals(0, c.durationHours(), "Поле не должно установиться, так как 0 < 1");
    }
}