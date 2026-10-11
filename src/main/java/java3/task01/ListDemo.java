package java3.task01;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/* Демонстрация операций List: добавление, вставка, удаление,
 * поиск дубликатов. И сравнение ArrayList vs LinkedList. */
public class ListDemo {

    /* Основные операции с List: порядок, вставка, удаление. */
    public static void basicOperations() {
        List<Registration> registrations = new ArrayList<>();

        Registration r1 = new Registration(1, 10);
        Registration r2 = new Registration(2, 10);
        Registration r3 = new Registration(3, 20);
        Registration r4 = new Registration(1, 10);   /* дубликат r1 */

        registrations.add(r1);
        registrations.add(r2);
        registrations.add(r3);
        registrations.add(r4);

        System.out.println("After add: " + registrations);

        registrations.add(1, new Registration(4, 20));
        System.out.println("After insert at 1: " + registrations);

        registrations.remove(1);
        System.out.println("After remove at 1: " + registrations);
    }

    /* Поиск дубликатов без Set — просто перебор. */
    public static void findDuplicates() {
        List<Registration> registrations = new ArrayList<>();
        registrations.add(new Registration(1, 10));
        registrations.add(new Registration(2, 10));
        registrations.add(new Registration(3, 20));
        registrations.add(new Registration(1, 10));
        registrations.add(new Registration(2, 10));
        registrations.add(new Registration(1, 10));

        System.out.println("Duplicates:");
        for (int i = 0; i < registrations.size(); i++) {
            boolean alreadyReported = false;
            for (int k = 0; k < i; k++) {
                if (registrations.get(k).equals(registrations.get(i))) {
                    alreadyReported = true;
                    break;
                }
            }
            if (alreadyReported) continue;

            int count = 0;
            for (Registration r : registrations) {
                if (r.equals(registrations.get(i))) count++;
            }
            if (count > 1) {
                System.out.println("  " + registrations.get(i) + " x" + count);
            }
        }
    }

    /* Измерение: чтение по индексу и обход.
     * Не бенчмарк — просто грубая оценка порядка величин. */
    public static void measure() {
        final int N = 100_000;

        List<Integer> arrayList = new ArrayList<>();
        List<Integer> linkedList = new LinkedList<>();
        for (int i = 0; i < N; i++) {
            arrayList.add(i);
            linkedList.add(i);
        }

        /* Чтение по индексу: 10_000 обращений к случайным позициям. */
        long t1 = System.nanoTime();
        long sum1 = 0;
        for (int i = 0; i < 10_000; i++) {
            int idx = (i * 7919) % N;
            sum1 += arrayList.get(idx);
        }
        long t2 = System.nanoTime();

        long sum2 = 0;
        for (int i = 0; i < 10_000; i++) {
            int idx = (i * 7919) % N;
            sum2 += linkedList.get(idx);
        }
        long t3 = System.nanoTime();

        System.out.println("Index access (10k) ArrayList:  " + (t2 - t1) / 1_000_000 + " ms");
        System.out.println("Index access (10k) LinkedList: " + (t3 - t2) / 1_000_000 + " ms");
        System.out.println("(checksum " + sum1 + " / " + sum2 + ")");

        /* Обход: for-each. */
        long t4 = System.nanoTime();
        long sum3 = 0;
        for (int v : arrayList) sum3 += v;
        long t5 = System.nanoTime();

        long sum4 = 0;
        for (int v : linkedList) sum4 += v;
        long t6 = System.nanoTime();

        System.out.println("Iteration ArrayList:  " + (t5 - t4) / 1_000_000 + " ms");
        System.out.println("Iteration LinkedList: " + (t6 - t5) / 1_000_000 + " ms");
        System.out.println("(checksum " + sum3 + " / " + sum4 + ")");
    }
}