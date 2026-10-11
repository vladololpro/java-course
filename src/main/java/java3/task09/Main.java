package java3.task09;

import java.util.ArrayList;
import java.util.List;
import model.Course;

/* Точка входа для Задачи 9. */
public class Main {

    public static void main(String[] args) {

        System.out.println("=== copy: producer extends, consumer super ===");
        List<OnlineCourse> src = new ArrayList<>();
        src.add(new OnlineCourse(1, "Java Online", 32));
        src.add(new OnlineCourse(2, "Algorithms Online", 40));

        List<Course> dst = new ArrayList<>();
        CopyUtils.copy(src, dst);
        System.out.println("dst size: " + dst.size());
        System.out.println("dst[0]: " + dst.get(0).title());

        System.out.println();
        System.out.println("=== copy into List<Object> ===");
        List<Object> objects = new ArrayList<>();
        CopyUtils.copy(src, objects);
        System.out.println("objects size: " + objects.size());

        System.out.println();
        System.out.println("=== List<? extends Course> - читать можно ===");
        List<? extends Course> producer = src;
        Course c = producer.get(0);
        System.out.println("read from ? extends Course: " + c.title());

        System.out.println();
        System.out.println("=== List<? super Course> - писать можно ===");
        List<? super Course> consumer = new ArrayList<Object>();
        consumer.add(new Course(3, "Databases", 30));
        System.out.println("added to ? super Course, size: " + consumer.size());

        System.out.println();
        System.out.println("=== List<? super Course> - читать только как Object ===");
        Object o = consumer.get(0);
        System.out.println("read from ? super Course: " + o.getClass().getSimpleName());
    }
}