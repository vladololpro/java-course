package java3.task02;

/* Модель кода курса без equals и hashCode.
 * Нужна, чтобы показать проблему: HashSet не удаляет дубликаты,
 * если у модели не переопределены equals и hashCode. */
public class CourseCodeWithoutEquals {

    private final String value;

    public CourseCodeWithoutEquals(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}