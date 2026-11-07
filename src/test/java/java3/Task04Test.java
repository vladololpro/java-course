package java3;

import java3.task04.Waitlist;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Task04Test {

    @Test
    void waitlist_isFifo() {
        Waitlist waitlist = new Waitlist();
        waitlist.add("student-1");
        waitlist.add("student-2");

        assertEquals("student-1", waitlist.peekNext());
        assertEquals(2, waitlist.size());
        assertEquals("student-1", waitlist.pollNext());
        assertEquals("student-2", waitlist.pollNext());
    }

    @Test
    void emptyWaitlist_returnsNullOnPeekAndPoll() {
        Waitlist waitlist = new Waitlist();

        assertTrue(waitlist.isEmpty());
        assertNull(waitlist.peekNext());
        assertNull(waitlist.pollNext());
    }
}
