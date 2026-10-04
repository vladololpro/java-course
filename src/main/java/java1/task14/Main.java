package java1.task14;

/* Демонстрация: композиция двух осей вместо 9 подклассов. */
public class Main {

    public static void main(String[] args) {

        CourseInfo[] courses = {
            new CourseInfo("Java Basics",  new OnlineFormat(),    new FreePayment()),
            new CourseInfo("Algorithms",  new ClassroomFormat(), new FixedPayment(10000)),
            new CourseInfo("System Design", new HybridFormat(),  new SubscriptionPayment(1500))
        };

        for (CourseInfo c : courses) {
            System.out.println(c.describe());
        }

        System.out.println();

        CourseFormat intensive = new CourseFormat() {
            @Override
            public String describe() {
                return "intensive";
            }
        };

        CourseInfo intensiveCourse = new CourseInfo("Java Sprint", intensive, new FixedPayment(20000));
        System.out.println(intensiveCourse.describe());

        PaymentPolicy hourly = new PaymentPolicy() {
            @Override
            public String describe() {
                return "hourly 500/h";
            }
        };

        CourseInfo hourlyCourse = new CourseInfo("Mentoring", new OnlineFormat(), hourly);
        System.out.println(hourlyCourse.describe());
    }
}