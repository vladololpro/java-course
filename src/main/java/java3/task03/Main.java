package java3.task03;

import model.Course;

/* Точка входа для Задачки 3. */
public class Main {

    public static void main(String[] args) {
        CourseIndex index = new CourseIndex();

        Course java = new Course(1, "Java Basics", 32);
        Course algo = new Course(2, "Algorithms", 40);
        Course javaDup = new Course(1, "Java Advanced", 50);

        System.out.println("=== addSafely ===");
        System.out.println("add java: " + index.addSafely(java));
        System.out.println("add algo: " + index.addSafely(algo));
        System.out.println("add javaDup: " + index.addSafely(javaDup));
        System.out.println("size: " + index.size());
        System.out.println("course 1 still: " + index.get(1).title());

        System.out.println();
        System.out.println("=== containsKey / get ===");
        System.out.println("contains 1: " + index.contains(1));
        System.out.println("contains 9: " + index.contains(9));
        System.out.println("get 1: " + index.get(1).title());
        System.out.println("get 9: " + index.get(9));

        System.out.println();
        System.out.println("=== putIfAbsent ===");
        Course existing = index.putIfAbsent(1, new Course(1, "Overwrite?", 99));
        System.out.println("putIfAbsent on existing id=1 returned: " + existing.title());
        System.out.println("course 1 still: " + index.get(1).title());

        Course added = index.putIfAbsent(3, new Course(3, "Databases", 30));
        System.out.println("putIfAbsent on new id=3 returned: " + added);
        System.out.println("course 3 now: " + index.get(3).title());

        System.out.println();
        System.out.println("=== computeIfAbsent ===");
        Course c1 = index.computeIfAbsent(1, "Should Not Be Used");
        System.out.println("computeIfAbsent on existing id=1: " + c1.title());

        Course c4 = index.computeIfAbsent(4, "Networks");
        System.out.println("computeIfAbsent on new id=4: " + c4.title());
        System.out.println("course 4 now: " + index.get(4).title());
    }
}