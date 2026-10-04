package java1.task12;

/* Sealed-иерархия результата зачисления.
 * Только три типа могут реализовать этот интерфейс. */
public sealed interface EnrollmentResult
        permits Accepted, Rejected, WaitListed {
}