package java1;

import java1.task02.ConsoleSender;
import java1.task02.EmailSender;
import java1.task02.NotificationSender;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Task02Test {

    @Test
    void overrideAndOverload_areChosenByDifferentRules() {
        try (OutputCapture out = new OutputCapture()) {
            NotificationSender sender = new EmailSender();

            sender.send("hello");
            sender.send("hello", 2);

            String text = out.text();

            assertAll(
                () -> assertTrue(text.contains("EmailSender.send: hello")),
                () -> assertTrue(text.contains("NotificationSender.send(priority=2): hello"))
            );
        }
    }

    @Test
    void baseImplementation_isUsedWhenMethodIsNotOverridden() {
        try (OutputCapture out = new OutputCapture()) {
            new NotificationSender().send("base");

            assertTrue(out.text().contains("NotificationSender.send: base"));
        }
    }

    @Test
    void consoleSender_overridesSend() {
        try (OutputCapture out = new OutputCapture()) {
            NotificationSender sender = new ConsoleSender();

            sender.send("console");

            assertTrue(out.text().contains("ConsoleSender.send: console"));
        }
    }
}
