package java3.task02;

/* Модель кода курса с equals и hashCode.
 * Это правильная модель: Set сможет распознавать дубликаты. */
public class CourseCode {

    private final String value;

    public CourseCode(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CourseCode c)) return false;
        return value.equals(c.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value;
    }
}