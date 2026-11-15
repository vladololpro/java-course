package java7;

public interface CapacityEnrollment {

    boolean enroll(long studentId);

    int enrolledCount();

    boolean isEnrolled(long studentId);

    int capacity();
}
