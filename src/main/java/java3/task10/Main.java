package java3.task10;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import model.Course;

/* Точка входа для Задачи 10. */
public class Main {

    public static void main(String[] args) {

        System.out.println("=== StableKey: tag не входит в hashCode ===");
        Set<StableKey> stable = new HashSet<>();
        StableKey sk = new StableKey(1, "before");
        stable.add(sk);
        System.out.println("contains before change: " + stable.contains(sk));

        sk.setTag("after");
        System.out.println("contains after change:  " + stable.contains(sk));
        System.out.println("size: " + stable.size());

        System.out.println();
        System.out.println("=== BrokenKey: tag входит в hashCode ===");
        Set<BrokenKey> broken = new HashSet<>();
        BrokenKey bk = new BrokenKey(1, "before");
        broken.add(bk);
        System.out.println("contains before change: " + broken.contains(bk));

        bk.setTag("after");
        System.out.println("contains after change:  " + broken.contains(bk));
        System.out.println("size: " + broken.size());
        System.out.println("set still has it: " + broken);

        System.out.println();
        System.out.println("=== Course в HashSet ===");
        Set<Course> courses = new HashSet<>();
        Course c1 = new Course(1, "Java", 32);
        Course c2 = new Course(1, "Java", 32);
        courses.add(c1);
        courses.add(c2);
        System.out.println("c1.equals(c2): " + c1.equals(c2));
        System.out.println("set size: " + courses.size());

        System.out.println();
        System.out.println("=== Course как ключ HashMap ===");
        Map<Long, Course> byId = new HashMap<>();
        Course a = new Course(1, "Java", 32);
        Course b = new Course(1, "Java", 32);
        byId.put(a.id(), a);
        byId.put(b.id(), b);
        System.out.println("map size: " + byId.size());
        System.out.println("id=1 -> " + byId.get(1L).title());
    }
}