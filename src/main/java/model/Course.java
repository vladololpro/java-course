package model;

public class Course {
    private long id;
    private String title;
    private int durationHours;
    private int completedHours;

    Course(long id, String title, int durationHours, int completedHours) {
        if (id > 0) this.id = id;
        else System.out.println("ERR");
        
        if (!title.isBlank() && !title.isEmpty()) this.title = title.trim();

        if (durationHours >= 1 && durationHours <= 100) this.durationHours = durationHours;
        
        if (completedHours < durationHours && completedHours >= 0) this.completedHours = completedHours;
    }

    public Course(long id, String title, int durationHours) {
        if (id > 0) this.id = id;
        else System.out.println("ERR");
        
        if (!title.isBlank() && !title.isEmpty()) this.title = title.trim();

        if (durationHours >= 1 && durationHours <= 300) this.durationHours = durationHours;
        
        this.completedHours = 0;
    }

    public long id() {
        return this.id;
    }
    public String title() {
        return this.title;
    }
    public int durationHours() {
        return this.durationHours;
    }
    public int completedHours() {
        return this.completedHours;
    }
    public int remainingHours() {
        return durationHours - completedHours;
    }
    public boolean isCompleted() {
        return remainingHours() == 0;
    }
    public void completeHours(int hours) {
        if (remainingHours() < hours) {
            System.out.println("ERR");
        } else {
            this.completedHours += hours;
        }
    }

    public static void main(String[] args) {
        Course course = new Course(10, "Java Fundamentals", 32); // 0
        course.completeHours(6);

        System.out.println(course.completedHours());
        System.out.println(course.remainingHours());
        System.out.println(course.isCompleted());
    }
}
