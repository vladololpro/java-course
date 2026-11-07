package java3;

import java3.task01.ListDemo;
import java3.task01.Registration;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Task01Test {

    @Test
    void listKeepsInsertionOrderAndSupportsInsertRemove() {
        Registration r1 = new Registration(1, 10);
        Registration r2 = new Registration(2, 10);
        Registration r3 = new Registration(3, 20);
        Registration r4 = new Registration(4, 20);
        List<Registration> registrations = new ArrayList<>();

        registrations.add(r1);
        registrations.add(r2);
        registrations.add(r3);
        assertEquals(List.of(r1, r2, r3), registrations);

        registrations.add(1, r4);
        assertEquals(List.of(r1, r4, r2, r3), registrations);

        registrations.remove(1);
        assertEquals(List.of(r1, r2, r3), registrations);
    }

    @Test
    void duplicateRegistrationsAreReportedOnce() {
        try (OutputCapture out = new OutputCapture()) {
            ListDemo.findDuplicates();

            String text = out.text();

            assertTrue(text.contains("Reg(student=1, course=10) x3"));
            assertTrue(text.contains("Reg(student=2, course=10) x2"));
            assertFalse(text.contains("Reg(student=3, course=20) x"));
        }
    }

    @Test
    void arrayListAndLinkedListKeepTheSameSequence() {
        List<Integer> arrayList = new ArrayList<>();
        List<Integer> linkedList = new LinkedList<>();
        for (int i = 0; i < 5; i++) {
            arrayList.add(i);
            linkedList.add(i);
        }

        assertEquals(arrayList, linkedList);
    }
}
