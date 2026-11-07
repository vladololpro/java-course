package java2;

import model.Course;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Task08Test {

    @Test
    void completeHours_withValidAmount_increasesProgress() {
        Course course = new Course(1, "Java Basics", 32);

        course.completeHours(6);

        assertEquals(6, course.completedHours(), "После 6 часов прогресс должен стать 6");
    }
}