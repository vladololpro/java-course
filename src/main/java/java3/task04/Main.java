package java3.task04;

/* Точка входа для Задачки 4. */
public class Main {

    public static void main(String[] args) {
        
        Waitlist waitlist = new Waitlist();

        System.out.println("=== FIFO: add and poll ===");
        waitlist.add("student-1");
        waitlist.add("student-2");
        waitlist.add("student-3");
        System.out.println("size: " + waitlist.size());
        System.out.println("peekNext: " + waitlist.peekNext());
        System.out.println("pollNext: " + waitlist.pollNext());
        System.out.println("pollNext: " + waitlist.pollNext());
        System.out.println("size after 2 polls: " + waitlist.size());
        System.out.println("peekNext: " + waitlist.peekNext());

        System.out.println();
        System.out.println("=== Empty queue ===");
        waitlist.pollNext();
        System.out.println("isEmpty: " + waitlist.isEmpty());
        System.out.println("peek on empty: " + waitlist.peekNext());
        System.out.println("poll on empty: " + waitlist.pollNext());

        System.out.println();
        System.out.println("=== add vs offer, remove vs poll, element vs peek ===");
        queuePairDemo();
    }

    static void queuePairDemo() {
        java.util.Queue<String> q = new java.util.ArrayDeque<>();

        q.offer("A");
        q.offer("B");
        System.out.println("after offer A, B: " + q);

        System.out.println("element: " + q.element());
        System.out.println("peek: " + q.peek());
        System.out.println("still: " + q);

        System.out.println("remove: " + q.remove());
        System.out.println("poll: " + q.poll());
        System.out.println("after remove and poll: " + q);
    }
}