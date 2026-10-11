package java3.task06;

import model.Course;

/* Точка входа для Задачи 6. */
public class Main {

    public static void main(String[] args) {

        Result<Course> ok = Result.success(new Course(1, "Java Basics", 32));
        Result<Course> fail = Result.failure("course not found");
        // Result<Course> broken = Result.success("not a course");

        System.out.println("=== Result<Course> ===");
        System.out.println("isSuccess: " + ok.isSuccess());
        System.out.println("value: " + ok.value().title());
        System.out.println("message: " + ok.message());

        System.out.println();
        System.out.println("isSuccess: " + fail.isSuccess());
        System.out.println("value: " + fail.value());
        System.out.println("message: " + fail.message());

        Result<String> okStr = Result.success("hello");
        Result<String> failStr = Result.failure("bad input");

        System.out.println();
        System.out.println("=== Result<String> ===");
        System.out.println("isSuccess: " + okStr.isSuccess());
        System.out.println("value: " + okStr.value());
        System.out.println("message: " + okStr.message());

        System.out.println();
        System.out.println("isSuccess: " + failStr.isSuccess());
        System.out.println("value: " + failStr.value());
        System.out.println("message: " + failStr.message());
    }
}