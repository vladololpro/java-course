package java3.task07;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import model.Course;

/* Точка входа для Задачи 7. */
public class Main {

    public static void main(String[] args) {

        System.out.println("=== firstOrThrow ===");
        List<String> names = new ArrayList<>();
        names.add("Anna");
        names.add("Bob");
        System.out.println("first: " + ListUtils.firstOrThrow(names));

        List<String> empty = new ArrayList<>();
        try {
            ListUtils.firstOrThrow(empty);
        } catch (IllegalArgumentException e) {
            System.out.println("on empty: " + e.getMessage());
        }

        System.out.println();
        System.out.println("=== swap ===");
        List<String> list = new ArrayList<>();
        list.add("A");
        list.add("B");
        list.add("C");
        System.out.println("before: " + list);
        ListUtils.swap(list, 0, 2);
        System.out.println("after swap(0,2): " + list);

        System.out.println();
        System.out.println("=== indexBy ===");
        List<Course> courses = new ArrayList<>();
        courses.add(new Course(1, "Java", 32));
        courses.add(new Course(2, "Algorithms", 40));
        courses.add(new Course(3, "Databases", 30));

        Map<Long, Course> byId = ListUtils.indexBy(courses, c -> c.id());
        System.out.println("map size: " + byId.size());
        System.out.println("id 2 -> " + byId.get(2L).title());

        System.out.println();
        System.out.println("=== indexBy with duplicate key ===");
        List<Course> dup = new ArrayList<>();
        dup.add(new Course(1, "Java", 32));
        dup.add(new Course(1, "Java Advanced", 40));
        try {
            ListUtils.indexBy(dup, c -> c.id());
        } catch (IllegalArgumentException e) {
            System.out.println("on duplicate: " + e.getMessage());
        }
    }
}