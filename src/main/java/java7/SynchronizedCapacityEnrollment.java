package java7;

import java.util.HashSet;
import java.util.Set;

public class SynchronizedCapacityEnrollment implements CapacityEnrollment {

    private final int capacity;
    private final Set<Long> students = new HashSet<>();

    public SynchronizedCapacityEnrollment(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("capacity не может быть отрицательной");
        }
        this.capacity = capacity;
    }

    @Override
    public synchronized boolean enroll(long studentId) {
        if (students.contains(studentId)) {
            return false;
        }
        if (students.size() >= capacity) {
            return false;
        }

        students.add(studentId);
        return true;
    }

    @Override
    public synchronized int enrolledCount() {
        return students.size();
    }

    @Override
    public synchronized boolean isEnrolled(long studentId) {
        return students.contains(studentId);
    }

    @Override
    public int capacity() {
        return capacity;
    }
}
