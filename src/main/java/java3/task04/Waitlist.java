package java3.task04;

import java.util.ArrayDeque;
import java.util.Queue;

/* Очередь ожидания студентов на курс. FIFO на ArrayDeque. */
public class Waitlist {

    private final Queue<String> queue = new ArrayDeque<>();

    public void add(String studentId) {
        queue.offer(studentId);
    }

    public String peekNext() {
        return queue.peek();
    }

    public String pollNext() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public int size() {
        return queue.size();
    }
}