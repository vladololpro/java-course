package java3.task07;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/* Утилиты для работы со списками. Три generic-метода. */
public class ListUtils {

    public static <T> T firstOrThrow(List<T> list) {
        if (list.isEmpty()) {
            throw new IllegalArgumentException("list is empty");
        }
        return list.get(0);
    }

    public static <T> void swap(List<T> list, int i, int j) {
        T tmp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, tmp);
    }

    public static <T, K> Map<K, T> indexBy(List<T> list, Function<T, K> keyExtractor) {
        Map<K, T> result = new HashMap<>();
        for (T item : list) {
            K key = keyExtractor.apply(item);
            if (result.containsKey(key)) {
                throw new IllegalArgumentException("duplicate key: " + key);
            }
            result.put(key, item);
        }
        return result;
    }
}