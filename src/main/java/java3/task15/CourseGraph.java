package java3.task15;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/* Ориентированный граф зависимостей курсов.
 * Вершина T. Ребро to -> from означает «from зависит от to».
 * Детерминированный порядок обеспечивается TreeMap и TreeSet. */
public class CourseGraph<T> {

    private final Map<T, Set<T>> edges = new TreeMap<>();
    private final Map<T, Integer> inDegree = new HashMap<>();

    public void addCourse(T course) {
        if (course == null) {
            throw new IllegalArgumentException("course is null");
        }
        edges.putIfAbsent(course, new TreeSet<>());
        inDegree.putIfAbsent(course, 0);
    }

    public void addDependency(T from, T to) {
        if (!edges.containsKey(from) || !edges.containsKey(to)) {
            throw new IllegalArgumentException(
                "both courses must be added first: " + from + ", " + to);
        }
        if (edges.get(to).add(from)) {
            inDegree.put(from, inDegree.get(from) + 1);
        }
    }

    public List<T> topologicalOrder() {
        Map<T, Integer> degree = new HashMap<>(inDegree);
        TreeSet<T> ready = new TreeSet<>();
        for (Map.Entry<T, Integer> e : degree.entrySet()) {
            if (e.getValue() == 0) {
                ready.add(e.getKey());
            }
        }

        List<T> result = new ArrayList<>();
        while (!ready.isEmpty()) {
            T current = ready.pollFirst();
            result.add(current);
            for (T dependent : edges.get(current)) {
                int d = degree.get(dependent) - 1;
                degree.put(dependent, d);
                if (d == 0) {
                    ready.add(dependent);
                }
            }
        }
        return result;
    }

    public List<T> findCycle() {
        List<T> order = topologicalOrder();
        List<T> remaining = new ArrayList<>();
        for (T course : edges.keySet()) {
            if (!order.contains(course)) {
                remaining.add(course);
            }
        }
        return remaining;
    }

    public List<String> edgesAsList() {
        List<String> lines = new ArrayList<>();
        for (Map.Entry<T, Set<T>> entry : edges.entrySet()) {
            for (T dependent : entry.getValue()) {
                lines.add(entry.getKey() + " -> " + dependent);
            }
        }
        return lines;
    }
}