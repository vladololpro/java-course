package java7;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicCapacityEnrollment implements CapacityEnrollment {

    private final int capacity;
    private final AtomicInteger occupiedSeats = new AtomicInteger();
    private final Set<Long> students = ConcurrentHashMap.newKeySet();

    public AtomicCapacityEnrollment(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("capacity не может быть отрицательной");
        }
        this.capacity = capacity;
    }

    @Override
    public boolean enroll(long studentId) {
        if (!students.add(studentId)) {
            return false;
        }

        while (true) {
            int occupied = occupiedSeats.get();
            if (occupied >= capacity) {
                students.remove(studentId);
                return false;
            }
            if (occupiedSeats.compareAndSet(occupied, occupied + 1)) {
                return true;
            }
        }
    }

    @Override
    public int enrolledCount() {
        return occupiedSeats.get();
    }

    @Override
    public boolean isEnrolled(long studentId) {
        return students.contains(studentId);
    }

    @Override
    public int capacity() {
        return capacity;
    }
}
