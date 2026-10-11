package java3.task05;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* Точка входа для Задачки 5.
 * Демонстрация ConcurrentModificationException и способов её избежать. */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== Broken: remove in for-each ===");
        broken();

        System.out.println();
        System.out.println("=== Fix 1: via Iterator ===");
        viaIterator();

        System.out.println();
        System.out.println("=== Fix 2: via removeIf ===");
        viaRemoveIf();

        System.out.println();
        System.out.println("=== Alternative: via copy ===");
        copyWay();
    }

    static void broken() {
        List<String> list = new ArrayList<>();
        list.add("A");
        list.add("B");
        list.add("C");
        list.add("D");

        try {
            for (String s : list) {
                if (s.equals("B")) {
                    list.remove(s);
                }
            }
            System.out.println("no exception, list: " + list);
        } catch (java.util.ConcurrentModificationException e) {
            System.out.println("ConcurrentModificationException: " + e.getMessage());
            System.out.println("list after failure: " + list);
        }
    }

    static void viaIterator() {
        List<String> list = new ArrayList<>();
        list.add("A");
        list.add("B");
        list.add("C");
        list.add("D");

        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            String s = it.next();
            if (s.equals("B")) {
                it.remove();
            }
        }
        System.out.println("after Iterator: " + list);
    }

    static void viaRemoveIf() {
        List<String> list = new ArrayList<>();
        list.add("A");
        list.add("B");
        list.add("C");
        list.add("D");

        list.removeIf(s -> s.equals("B"));
        System.out.println("after removeIf: " + list);
    }

    static void copyWay() {
        List<String> list = new ArrayList<>();
        list.add("A");
        list.add("B");
        list.add("C");
        list.add("D");

        List<String> copy = new ArrayList<>(list);
        for (String s : copy) {
            if (s.equals("B")) {
                list.remove(s);
            }
        }
        System.out.println("after copy way: " + list);
    }
}