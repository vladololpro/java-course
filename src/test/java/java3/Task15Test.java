package java3;

import java3.task15.CourseGraph;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Task15Test {

    @Test
    void topologicalOrder_respectsDependenciesAndIsDeterministic() {
        CourseGraph<String> graph = new CourseGraph<>();
        graph.addCourse("Java");
        graph.addCourse("Algorithms");
        graph.addCourse("Databases");
        graph.addCourse("Web");
        graph.addDependency("Algorithms", "Java");
        graph.addDependency("Databases", "Algorithms");
        graph.addDependency("Web", "Java");
        graph.addDependency("Web", "Databases");

        assertEquals(
            List.of("Java", "Algorithms", "Databases", "Web"),
            graph.topologicalOrder()
        );
        assertTrue(graph.findCycle().isEmpty());
    }

    @Test
    void cycle_isDetected() {
        CourseGraph<String> graph = new CourseGraph<>();
        graph.addCourse("A");
        graph.addCourse("B");
        graph.addCourse("C");
        graph.addDependency("A", "B");
        graph.addDependency("B", "C");
        graph.addDependency("C", "A");

        assertTrue(graph.topologicalOrder().isEmpty());
        assertEquals(List.of("A", "B", "C"), graph.findCycle());
    }

    @Test
    void missingVertexIsRejected() {
        CourseGraph<String> graph = new CourseGraph<>();
        graph.addCourse("X");

        IllegalArgumentException error = assertThrows(
            IllegalArgumentException.class,
            () -> graph.addDependency("X", "Y")
        );

        assertTrue(error.getMessage().contains("both courses must be added first"));
    }

    @Test
    void duplicateDependencyIsCountedOnce() {
        CourseGraph<String> graph = new CourseGraph<>();
        graph.addCourse("A");
        graph.addCourse("B");
        graph.addDependency("A", "B");
        graph.addDependency("A", "B");

        assertEquals(List.of("B", "A"), graph.topologicalOrder());
    }
}
