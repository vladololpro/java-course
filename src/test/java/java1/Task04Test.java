package java1;

import java1.task04.AbstractNotificationSender;
import java1.task04.ConsoleSender;
import java1.task04.EmailSender;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Task04Test {

    @Test
    void emailSender_runsCommonTemplate() {
        try (OutputCapture out = new OutputCapture()) {
            new EmailSender("student@example.com").send("hello");

            String text = out.text();

            assertAll(
                () -> assertTrue(text.contains("Sending to student@example.com...")),
                () -> assertTrue(text.contains("[EMAIL to student@example.com] hello")),
                () -> assertTrue(text.contains("Done."))
            );
        }
    }

    @Test
    void consoleSender_usesItsOwnDoSend() {
        try (OutputCapture out = new OutputCapture()) {
            new ConsoleSender("console-user").send("hello");

            assertTrue(out.text().contains("[CONSOLE -> console-user] hello"));
        }
    }

    @Test
    void blankRecipient_isRejectedBeforeDoSend() {
        try (OutputCapture out = new OutputCapture()) {
            new EmailSender("   ").send("hello");

            String text = out.text();

            assertAll(
                () -> assertTrue(text.contains("ERR: recipient is empty")),
                () -> assertFalse(text.contains("[EMAIL"))
            );
        }
    }

    @Test
    void sendIsFinalAndDoSendIsAbstract() throws Exception {
        Method send = AbstractNotificationSender.class.getMethod("send", String.class);
        Method doSend = AbstractNotificationSender.class.getDeclaredMethod("doSend", String.class);

        assertAll(
            () -> assertTrue(Modifier.isFinal(send.getModifiers())),
            () -> assertTrue(Modifier.isAbstract(doSend.getModifiers()))
        );
    }
}
