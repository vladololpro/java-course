package java1;

import java1.task10.CombinedBoth;
import java1.task10.CustomBoth;
import java1.task10.FixedBoth;
import java1.task10.Logger;
import java1.task10.Notifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Task10Test {

    @Test
    void defaultMethods_areAvailableInEachInterface() {
        Notifier notifier = new Notifier() {
        };
        Logger logger = new Logger() {
        };

        assertEquals("Notifier", notifier.describe());
        assertEquals("Logger", logger.describe());
    }

    @Test
    void conflictCanBeResolvedByChoosingOneInterface() {
        assertEquals("Notifier", new FixedBoth().describe());
    }

    @Test
    void conflictCanBeResolvedByCombiningBothInterfaces() {
        assertEquals("Notifier+Logger", new CombinedBoth().describe());
    }

    @Test
    void conflictCanBeResolvedByCustomImplementation() {
        assertEquals("Custom", new CustomBoth().describe());
    }
}
