package java2.domain;

import java.util.Locale;
import java.util.regex.Pattern;

public final class CourseCode {

    private static final Pattern PATTERN = Pattern.compile("[A-Z]{2,4}-\\d{3}");

    private final String value;

    private CourseCode(String value) {
        this.value = value;
    }

    public static CourseCode parse(String raw) {
        if (raw == null) {
            throw new IllegalArgumentException("Course code is null");
        }

        String normalized = raw.trim().toUpperCase(Locale.ROOT);
        if (!PATTERN.matcher(normalized).matches()) {
            throw new IllegalArgumentException("Invalid course code: " + raw);
        }
        return new CourseCode(normalized);
    }

    public static boolean isValid(String raw) {
        if (raw == null) {
            return false;
        }

        String normalized = raw.trim().toUpperCase(Locale.ROOT);
        return PATTERN.matcher(normalized).matches();
    }

    public String value() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
