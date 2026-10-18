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
            "15.10.2024", "01.01.2025", "31.02.2024", "30.02.2023",
            "15.10.2024!", "15-10-2024", "1.1.2024"
        };

        for (String input : testInputs) {
            try {
                LocalDate date = parseDate(input);
                System.out.println("OK: \"" + input + "\" -> " + date);
            } catch (InvalidDateFieldException e) {
                System.out.println("FAIL: \"" + input + "\"");
                System.out.println("  Поле: " + e.getFieldName());
                System.out.println("  Значение: " + e.getInputValue());
                System.out.println("  Причина: " + e.getMessage());
            }
        }
    }

    private LocalDate parseDate(String input) {
        try {
            return LocalDate.parse(input, FORMATTER);
        } catch (DateTimeParseException e) {
            throw new InvalidDateFieldException("date", input, 
                e.getCause() != null ? e.getCause().getMessage() : "invalid format");
        }
    }
}