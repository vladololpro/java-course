package java1.task14;

/* Курс содержит обе оси через композицию, а не наследует их.
 * Формат и оплата независимы - их можно свободно комбинировать. */
public class CourseInfo {

    private final String title;
    private final CourseFormat format;
    private final PaymentPolicy payment;

    public CourseInfo(String title, CourseFormat format, PaymentPolicy payment) {
        this.title = title;
        this.format = format;
        this.payment = payment;
    }

    public String describe() {
        return title + " [" + format.describe() + ", " + payment.describe() + "]";
    }
}