package java3.task09;

import java.util.List;

/* Утилита копирования из producer-списка в consumer-список.
 * PECS: Producer Extends, Consumer Super. */
public class CopyUtils {

    public static <T> void copy(List<? extends T> src, List<? super T> dst) {
        for (T item : src) {
            dst.add(item);
        }
    }
}