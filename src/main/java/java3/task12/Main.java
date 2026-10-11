package java3.task12;

import java.util.List;
import model.Course;

/* Точка входа для Задачи 12. */
public class Main {

    public static void main(String[] args) {

        System.out.println("=== save and findById ===");
        Repository<Long, Course> repo = new Repository<>();
        Course java = new Course(1, "Java", 32);
        Course algo = new Course(2, "Algorithms", 40);
        repo.save(java.id(), java);
        repo.save(algo.id(), algo);

        Course found = repo.findById(1L);
        System.out.println("found: " + found.title());

        System.out.println();
        System.out.println("=== save with same id (upsert) ===");
        Course javaNew = new Course(1, "Java Advanced", 50);
        repo.save(javaNew.id(), javaNew);
        System.out.println("after upsert: " + repo.findById(1L).title());

        System.out.println();
        System.out.println("=== findAll returns copy ===");
        List<Course> all = repo.findAll();
        System.out.println("all size: " + all.size());
        all.clear();
        System.out.println("after client clear, repo size still: " + repo.findAll().size());

        System.out.println();
        System.out.println("=== deleteById ===");
        repo.deleteById(2L);
        System.out.println("findById(2): " + repo.findById(2L));
        System.out.println("size: " + repo.findAll().size());

        System.out.println();
        System.out.println("=== null checks ===");
        try {
            repo.save(null, java);
        } catch (IllegalArgumentException e) {
            System.out.println("save null id: " + e.getMessage());
        }
        try {
            repo.save(1L, null);
        } catch (IllegalArgumentException e) {
            System.out.println("save null entity: " + e.getMessage());
        }
    }
}