package java2;

import model.Course;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Task11Test {

    @Test
    void completeHours_withInvalidAmount_shouldNotChangeState() {
        Course course = new Course(1, "Java", 32);
        course.completeHours(10);

        course.completeHours(100); 
        
        assertEquals(10, course.completedHours(), "Прогресс не должен измениться при ошибке");
    }
}