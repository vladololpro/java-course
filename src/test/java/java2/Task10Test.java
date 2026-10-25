package java2;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class Task10Test {
    
    private boolean isValid(String code) {
        return code.matches("[A-Z]{2,4}-\\d{3}");
    }

    @ParameterizedTest(name = "Код: {0}, Ожидание: {1}")
    @CsvSource({
        "JAVA-101, true",
        "CS-999, true",
        "java-101, false",
        "JAVA101, false",
        "JAVA-101!, false",
        "TOOLONG-101, false"
    })
    void isValidCode_shouldMatchExpected(String code, boolean expected) {
        assertEquals(expected, isValid(code), "Код " + code + " должен дать " + expected);
    }

    @ParameterizedTest(name = "Невалидный код: {0}")
    @ValueSource(strings = {"", " ", "JAVA_101", "101", "JAVA-10"})
    void invalidCodes_shouldBeRejected(String code) {
        assertFalse(isValid(code), "Код " + code + " должен быть отклонен");
    }
}