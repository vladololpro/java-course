package java1;

import java1.task12.Accepted;
import java1.task12.Pending;
import java1.task12.Rejected;
import java1.task12.ResultPrinter;
import java1.task12.WaitListed;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Task12Test {

    @Test
    void describe_handlesAccepted() {
        assertEquals(
            "Student 1 accepted to course 10",
            ResultPrinter.describe(new Accepted(1, 10))
        );
    }

    @Test
    void describe_handlesRejected() {
        assertEquals(
            "Student 1 rejected from course 10: NO_SEATS",
            ResultPrinter.describe(new Rejected(1, 10, "NO_SEATS"))
        );
    }

    @Test
    void describe_handlesWaitListed() {
        assertEquals(
            "Student 1 waitlisted for course 10, position 3",
            ResultPrinter.describe(new WaitListed(1, 10, 3))
        );
    }

    @Test
    void describe_handlesPending() {
        assertEquals(
            "Student 1 pending for course 10",
            ResultPrinter.describe(new Pending(1, 10))
        );
    }
}
