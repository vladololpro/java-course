package java8;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.LockSupport;
import java.util.function.Consumer;

public class AsyncCourseService {

    private static final Map<Long, Course> COURSES = Map.of(
            1L, new Course(1, "JAVA-1", "Основы Java", 32),
            2L, new Course(2, "SQL-1", "Базы данных", 20)
    );

    private static final Map<Long, User> USERS = Map.of(
            101L, new User(101, "Анна"),
            102L, new User(102, "Борис")
    );

    private static final Map<Long, List<Enrollment>> ENROLLMENTS = Map.of(
            101L, List.of(
                    new Enrollment(101, "JAVA-1", true),
                    new Enrollment(101, "SQL-1", false)
            ),
            102L, List.of(new Enrollment(102, "JAVA-1", false))
    );

    public record Course(long id, String code, String title, int duration) {
    }

    public record User(long id, String name) {
    }

    public record Enrollment(long userId, String courseCode, boolean completed) {
    }

    public record CardTrace(String card, String loadThread, String formatThread, String printThread) {
    }

    private record LoadedCourse(Course course, String thread) {
    }

    public CompletableFuture<Void> buildCard(
            long courseId,
            ExecutorService executor,
            Consumer<CardTrace> sink
    ) {
        return CompletableFuture.supplyAsync(() -> {
            String thread = currentThreadName();
            return new LoadedCourse(loadCourse(courseId), thread);
        }, executor)
                .thenApply(loaded -> {
                    Course course = loaded.course();
                    String card = course.code() + " | " + course.title()
                            + " | " + course.duration() + "h";
                    return new CardTrace(card, loaded.thread(), currentThreadName(), null);
                })
                .thenAccept(trace -> sink.accept(new CardTrace(
                        trace.card(),
                        trace.loadThread(),
                        trace.formatThread(),
                        currentThreadName()
                )));
    }

    public CompletableFuture<List<Enrollment>> loadUserEnrollments(
            long userId,
            ExecutorService executor
    ) {
        return CompletableFuture.supplyAsync(() -> loadUser(userId), executor)
                .thenCompose(user -> CompletableFuture.supplyAsync(
                        () -> loadEnrollments(user.id()), executor));
    }

    public static void demo() throws InterruptedException, ExecutionException, TimeoutException {
        ExecutorService executor = Executors.newFixedThreadPool(3, ExecutorSupport.named("course-io"));
        try {
            List<CardTrace> traces = new ArrayList<>();
            new AsyncCourseService().buildCard(1, executor, traces::add)
                    .get(3, TimeUnit.SECONDS);

            CardTrace trace = traces.get(0);
            System.out.println("Карточка: " + trace.card());
            System.out.println("Потоки: load=" + trace.loadThread()
                    + ", format=" + trace.formatThread()
                    + ", accept=" + trace.printThread());

            List<Enrollment> enrollments = new AsyncCourseService()
                    .loadUserEnrollments(101, executor)
                    .get(3, TimeUnit.SECONDS);
            System.out.println("Записи пользователя: " + enrollments);
        } finally {
            ExecutorSupport.shutdownAndAwait(executor);
        }
    }

    private static Course loadCourse(long id) {
        block(Duration.ofMillis(10));
        Course course = COURSES.get(id);
        if (course == null) {
            throw new IllegalArgumentException("Курс " + id + " не найден");
        }
        return course;
    }

    private static User loadUser(long id) {
        block(Duration.ofMillis(10));
        User user = USERS.get(id);
        if (user == null) {
            throw new IllegalArgumentException("Пользователь " + id + " не найден");
        }
        return user;
    }

    private static List<Enrollment> loadEnrollments(long userId) {
        block(Duration.ofMillis(10));
        List<Enrollment> enrollments = ENROLLMENTS.get(userId);
        if (enrollments == null) {
            throw new IllegalArgumentException("Записи пользователя " + userId + " не найдены");
        }
        return enrollments;
    }

    private static void block(Duration duration) {
        LockSupport.parkNanos(duration.toNanos());
    }

    private static String currentThreadName() {
        return Thread.currentThread().getName();
    }
}
