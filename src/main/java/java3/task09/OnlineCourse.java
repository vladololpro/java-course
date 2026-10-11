package java3.task09;

import model.Course;

/* Подтип Course: онлайн-курс. */
public class OnlineCourse extends Course {

    public OnlineCourse(long id, String title, int durationHours) {
        super(id, title, durationHours);
    }

    @Override
    public String toString() {
        return "OnlineCourse(" + id() + ", " + title() + ")";
    }
}