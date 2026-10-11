package java3.task14;

/* Точка входа для Задачи 14. */
public class Main {

    public static void main(String[] args) {

        System.out.println("=== eviction order ===");
        LruCache<String, Integer> cache = new LruCache<>(3);
        cache.put("A", 1);
        cache.put("B", 2);
        cache.put("C", 3);
        System.out.println("after put A, B, C: " + cache);

        cache.get("A");
        System.out.println("after get A: " + cache);

        cache.put("D", 4);
        System.out.println("after put D: " + cache);

        cache.put("E", 5);
        System.out.println("after put E: " + cache);

        System.out.println();
        System.out.println("=== key stability ===");
        LruCache<String, Integer> cache2 = new LruCache<>(2);
        cache2.put("X", 10);
        cache2.put("Y", 20);
        cache2.put("X", 11);
        System.out.println("after put X=10, Y=20, X=11: " + cache2);
        System.out.println("X value: " + cache2.get("X"));
        System.out.println("Y value: " + cache2.get("Y"));

        System.out.println();
        System.out.println("=== null policy ===");
        try {
            cache2.put(null, 1);
        } catch (IllegalArgumentException e) {
            System.out.println("put null key: " + e.getMessage());
        }
        try {
            cache2.put("Z", null);
        } catch (IllegalArgumentException e) {
            System.out.println("put null value: " + e.getMessage());
        }
        try {
            cache2.get(null);
        } catch (IllegalArgumentException e) {
            System.out.println("get null key: " + e.getMessage());
        }
    }
}