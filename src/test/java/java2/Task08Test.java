package java2;

import model.Course;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/* Тест успешного добавления часов в Course. */
class Task08Test {

    @Test
    void completeHours_shouldIncreaseProgress() {
        Course course = new Course(1, "Java Basics", 32);
        assertEquals(0, course.completedHours(), "Изначально прогресс должен быть 0");

        course.completeHours(6);

        assertEquals(6, course.completedHours(), "Прогресс должен увеличиться до 6");
        assertEquals(26, course.remainingHours(), "Остаток должен быть 26");
    }
}