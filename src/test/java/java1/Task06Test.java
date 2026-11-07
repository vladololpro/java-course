package java1;

import java1.task06.BrokenEmailSender;
import java1.task06.BrokenSender;
import java1.task06.FixedEmailSender;
import java1.task06.FixedSender;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Task06Test {

    @Test
    void brokenSubclass_overloadsInsteadOfOverrides() {
        BrokenSender sender = new BrokenEmailSender();

        assertEquals("base.send: hello", sender.send("hello"));
    }

    @Test
    void brokenSubclass_objectOverloadIsAvailableDirectly() {
        BrokenEmailSender sender = new BrokenEmailSender();

        assertEquals("broken.send(Object): hello", sender.send((Object) "hello"));
    }

    @Test
    void fixedSubclass_overridesParentMethod() {
        FixedSender sender = new FixedEmailSender();

        assertEquals("email.send: hello", sender.send("hello"));
    }
}
