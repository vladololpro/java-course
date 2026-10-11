package java3.task15;

import java.util.List;

/* Точка входа для Задачи 15. */
public class Main {

    public static void main(String[] args) {

        System.out.println("=== valid order ===");
        CourseGraph<String> g1 = new CourseGraph<>();
        g1.addCourse("Java");
        g1.addCourse("Algorithms");
        g1.addCourse("Databases");
        g1.addCourse("Web");
        g1.addDependency("Algorithms", "Java");
        g1.addDependency("Databases", "Algorithms");
        g1.addDependency("Web", "Java");
        g1.addDependency("Web", "Databases");
        System.out.println("graph: " + g1.edgesAsList());
        System.out.println("order: " + arrow(g1.topologicalOrder()));
        System.out.println("cycle: " + arrowCycle(g1.findCycle()));

        System.out.println();
        System.out.println("=== graph with cycle ===");
        CourseGraph<String> g2 = new CourseGraph<>();
        g2.addCourse("A");
        g2.addCourse("B");
        g2.addCourse("C");
        g2.addDependency("A", "B");
        g2.addDependency("B", "C");
        g2.addDependency("C", "A");
        System.out.println("graph: " + g2.edgesAsList());
        System.out.println("order: " + arrow(g2.topologicalOrder()));
        System.out.println("cycle: " + arrowCycle(g2.findCycle()));

        System.out.println();
        System.out.println("=== missing vertex ===");
        CourseGraph<String> g3 = new CourseGraph<>();
        g3.addCourse("X");
        try {
            g3.addDependency("X", "Y");
        } catch (IllegalArgumentException e) {
            System.out.println("error: " + e.getMessage());
        }
    }

    static String arrow(List<String> order) {
        if (order.isEmpty()) {
            return "(empty)";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < order.size(); i++) {
            if (i > 0) sb.append(" -> ");
            sb.append(order.get(i));
        }
        return sb.toString();
    }

    static String arrowCycle(List<String> order) {
        if (order.isEmpty()) {
            return "(empty)";
        }
        StringBuilder sb = new StringBuilder();
        for (String s : order) {
            sb.append(s).append(" -> ");
        }
        sb.append(order.get(0));
        return sb.toString();
    }
}