package java3.task02;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/* Демонстрация Set: удаление дубликатов CourseCode
 * с сохранением порядка первого появления.
 * И показ влияния equals/hashCode. */
public class SetDemo {
    
    public static void showProblem() {
        List<CourseCodeWithoutEquals> list = new ArrayList<>();
        list.add(new CourseCodeWithoutEquals("CS101"));
        list.add(new CourseCodeWithoutEquals("MATH201"));
        list.add(new CourseCodeWithoutEquals("CS101"));
        list.add(new CourseCodeWithoutEquals("PHYS100"));
        list.add(new CourseCodeWithoutEquals("MATH201"));

        Set<CourseCodeWithoutEquals> set = new HashSet<>(list);

        System.out.println("Input:  " + list);
        System.out.println("HashSet: " + set);
        System.out.println("Size: " + set.size() + " (ожидалось 3, но дубликаты не убрались)");
    }

    public static void solve() {
        List<CourseCode> list = new ArrayList<>();
        list.add(new CourseCode("CS101"));
        list.add(new CourseCode("MATH201"));
        list.add(new CourseCode("CS101"));
        list.add(new CourseCode("PHYS100"));
        list.add(new CourseCode("MATH201"));

        Set<CourseCode> set = new LinkedHashSet<>(list);

        System.out.println("Input:        " + list);
        System.out.println("LinkedHashSet: " + set);
        System.out.println("Size: " + set.size() + " (дубликаты убраны, порядок сохранён)");
    }
}