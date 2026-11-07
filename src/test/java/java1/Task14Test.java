package java1;

import java1.task14.ClassroomFormat;
import java1.task14.CourseFormat;
import java1.task14.CourseInfo;
import java1.task14.FixedPayment;
import java1.task14.FreePayment;
import java1.task14.HybridFormat;
import java1.task14.OnlineFormat;
import java1.task14.PaymentPolicy;
import java1.task14.SubscriptionPayment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Task14Test {

    @Test
    void courseInfo_combinesIndependentAxes() {
        assertEquals(
            "Java [online, free]",
            new CourseInfo("Java", new OnlineFormat(), new FreePayment()).describe()
        );
        assertEquals(
            "Algorithms [classroom, fixed 10000]",
            new CourseInfo("Algorithms", new ClassroomFormat(), new FixedPayment(10000)).describe()
        );
        assertEquals(
            "Design [hybrid, subscription 1500/month]",
            new CourseInfo("Design", new HybridFormat(), new SubscriptionPayment(1500)).describe()
        );
    }

    @Test
    void newFormatAndPayment_canBeAddedWithoutChangingCourseInfo() {
        CourseFormat intensive = () -> "intensive";
        PaymentPolicy hourly = () -> "hourly 500/h";

        CourseInfo course = new CourseInfo("Java Sprint", intensive, hourly);

        assertEquals("Java Sprint [intensive, hourly 500/h]", course.describe());
    }
}
