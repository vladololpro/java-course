package java4.time;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

/* Класс для строгого парсинга дат. */
public class DateParser {

    private static final DateTimeFormatter FORMATTER = 
        DateTimeFormatter.ofPattern("dd.MM.uuuu").withResolverStyle(ResolverStyle.STRICT);

    public void run() {
        String[] testInputs = {
            "15.10.2024",
            "01.01.2025",
            "31.02.2024",
            "30.02.2023",
            "15.10.2024!",
            "15-10-2024",
            "1.1.2024"
        };

        for (String input : testInputs) {
            parseAndReport(input);
        }
    }

    private void parseAndReport(String input) {
        try {
            LocalDate date = LocalDate.parse(input, FORMATTER);
            System.out.println("OK: \"" + input + "\" -> " + date);
        } catch (DateTimeParseException e) {
            System.out.println("FAIL: \"" + input + "\"");
            System.out.println("  Поле: не удалось распарсить дату");
            System.out.println("  Значение: " + e.getParsedString());
            if (e.getCause() != null) {
                System.out.println("  Причина: " + e.getCause().getMessage());
            }
        }
    }
}