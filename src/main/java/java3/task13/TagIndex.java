package java3.task13;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* Обратный индекс: тег -> множество id курсов.
 * Поддерживает добавление и удаление курса, пересечение тегов.
 * Пустые множества не остаются в индексе. */
public class TagIndex {

    private final Map<String, Set<Long>> index = new HashMap<>();

    public void addCourse(long courseId, Set<String> tags) {
        for (String tag : tags) {
            Set<Long> ids = index.get(tag);
            if (ids == null) {
                ids = new HashSet<>();
                index.put(tag, ids);
            }
            ids.add(courseId);
        }
    }

    public void removeCourse(long courseId) {
        Iterator<Map.Entry<String, Set<Long>>> it = index.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Set<Long>> entry = it.next();
            Set<Long> ids = entry.getValue();
            ids.remove(courseId);
            if (ids.isEmpty()) {
                it.remove();
            }
        }
    }

    public Set<Long> findByTags(Set<String> tags) {
        Set<Long> result = new HashSet<>();
        boolean first = true;
        for (String tag : tags) {
            Set<Long> ids = index.get(tag);
            if (ids == null) {
                return new HashSet<>();
            }
            if (first) {
                result.addAll(ids);
                first = false;
            } else {
                result.retainAll(ids);
            }
        }
        return result;
    }

    public int tagCount() {
        return index.size();
    }
}