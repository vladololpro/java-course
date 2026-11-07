package java1;

import java1.task13.BrokenNotification;
import java1.task13.EmailSender;
import java1.task13.NotificationSender;
import java1.task13.PushSender;
import java1.task13.SmsSender;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class Task13Test {

    @Test
    void brokenNotification_usesTypeCode() {
        try (OutputCapture out = new OutputCapture()) {
            new BrokenNotification("email", "student@example.com").send("hello");
            new BrokenNotification("unknown", "student@example.com").send("hello");

            String text = out.text();

            assertTrue(text.contains("[EMAIL to student@example.com] hello"));
            assertTrue(text.contains("Unknown type: unknown"));
        }
    }

    @Test
    void polymorphicSenders_doNotNeedTypeChecks() {
        try (OutputCapture out = new OutputCapture()) {
            NotificationSender[] senders = {
                new EmailSender("student@example.com"),
                new SmsSender("+7-999-000-00-00"),
                new PushSender("device-1")
            };

            for (NotificationSender sender : senders) {
                sender.send("hello");
            }

            String text = out.text();

            assertTrue(text.contains("[EMAIL to student@example.com] hello"));
            assertTrue(text.contains("[SMS to +7-999-000-00-00] hello"));
            assertTrue(text.contains("[PUSH to device-1] hello"));
        }
    }
}
