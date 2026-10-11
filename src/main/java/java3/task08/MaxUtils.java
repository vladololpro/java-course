package java3.task08;

import java.util.List;

/* Утилита для поиска максимума в списке.
 * Bound: T должен реализовывать Comparable<T> -
 * значит объекты типа T можно сравнивать между собой. */
public class MaxUtils {

    public static <T extends Comparable<T>> T max(List<T> list) {
        if (list.isEmpty()) {
            throw new IllegalArgumentException("list is empty");
        }
        T best = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            T current = list.get(i);
            if (current.compareTo(best) > 0) {
                best = current;
            }
        }
        return best;
    }
}