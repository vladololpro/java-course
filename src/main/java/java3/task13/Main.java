package java3.task13;

import java.util.Set;

/* Точка входа для Задачи 13. */
public class Main {

    public static void main(String[] args) {

        TagIndex idx = new TagIndex();

        System.out.println("=== add courses ===");
        idx.addCourse(1, Set.of("java", "backend"));
        idx.addCourse(2, Set.of("java", "algorithms"));
        idx.addCourse(3, Set.of("java", "backend", "databases"));
        System.out.println("added: course 1 -> [java, backend]");
        System.out.println("added: course 2 -> [java, algorithms]");
        System.out.println("added: course 3 -> [java, backend, databases]");
        System.out.println("tag count: " + idx.tagCount());

        System.out.println();
        System.out.println("=== findByTags (intersection) ===");
        System.out.println("java + backend:    " + idx.findByTags(Set.of("java", "backend")));
        System.out.println("java + databases:  " + idx.findByTags(Set.of("java", "databases")));
        System.out.println("java + algorithms: " + idx.findByTags(Set.of("java", "algorithms")));
        System.out.println("java + unknown:    " + idx.findByTags(Set.of("java", "unknown")));

        System.out.println();
        System.out.println("=== removeCourse(1) ===");
        idx.removeCourse(1);
        System.out.println("removed: course 1");
        System.out.println("java:       " + idx.findByTags(Set.of("java")));
        System.out.println("backend:    " + idx.findByTags(Set.of("backend")));
        System.out.println("algorithms: " + idx.findByTags(Set.of("algorithms")));
        System.out.println("databases:  " + idx.findByTags(Set.of("databases")));
        System.out.println("tag count: " + idx.tagCount());

        System.out.println();
        System.out.println("=== removeCourse(3) ===");
        idx.removeCourse(3);
        System.out.println("removed: course 3");
        System.out.println("java:       " + idx.findByTags(Set.of("java")));
        System.out.println("backend:    " + idx.findByTags(Set.of("backend")));
        System.out.println("algorithms: " + idx.findByTags(Set.of("algorithms")));
        System.out.println("databases:  " + idx.findByTags(Set.of("databases")));
        System.out.println("tag count: " + idx.tagCount());
    }
}