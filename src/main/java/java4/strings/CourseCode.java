package java4.strings;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* Класс для проверки и извлечения частей кода курса. */
public class CourseCode {

    private static final Pattern PATTERN = Pattern.compile("([A-Z]{2,4})-(\\d{3})");

    public void run() {
        String[] validCodes = {"JAVA-101", "CS-999", "WEB-001"};
        String[] invalidCodes = {
            "java-101",
            "JAVA101",
            "JAVA-10",
            "JAVA-101!",
            " JAVA-101",
            "JAVA-101 ",
            "ПРОГ-101",
            "TOOLONG-101"
        };

        System.out.println("=== Валидные коды ===");
        for (String code : validCodes) {
            processCode(code);
        }

        System.out.println("\n=== Невалидные коды ===");
        for (String code : invalidCodes) {
            processCode(code);
        }
    }

    private void processCode(String code) {
        Matcher matcher = PATTERN.matcher(code);
        
        if (matcher.matches()) {
            String prefix = matcher.group(1);
            String number = matcher.group(2);
            System.out.println("OK: \"" + code + "\" -> prefix=" + prefix + ", number=" + number);
        } else {
            System.out.println("FAIL: \"" + code + "\" (невалидный)");
        }
    }
}