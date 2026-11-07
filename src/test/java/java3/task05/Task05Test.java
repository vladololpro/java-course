package java3.task05;

import java3.OutputCapture;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class Task05Test {

    @Test
    void removeInForEach_causesConcurrentModificationException() {
        try (OutputCapture out = new OutputCapture()) {
            Main.broken();

            String text = out.text();

            assertTrue(text.contains("ConcurrentModificationException"));
            assertTrue(text.contains("list after failure: [A, C, D]"));
        }
    }

    @Test
    void iteratorRemoval_worksWithoutException() {
        try (OutputCapture out = new OutputCapture()) {
            Main.viaIterator();

            assertTrue(out.text().contains("after Iterator: [A, C, D]"));
        }
    }

    @Test
    void removeIf_worksWithoutException() {
        try (OutputCapture out = new OutputCapture()) {
            Main.viaRemoveIf();

            assertTrue(out.text().contains("after removeIf: [A, C, D]"));
        }
    }

    @Test
    void copyBeforeRemoval_alsoWorks() {
        try (OutputCapture out = new OutputCapture()) {
            Main.copyWay();

            assertTrue(out.text().contains("after copy way: [A, C, D]"));
        }
    }
}
