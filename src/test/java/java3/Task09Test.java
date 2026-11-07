package java3;

import java3.task09.CopyUtils;
import java3.task09.OnlineCourse;
import model.Course;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Task09Test {

    @Test
    void copy_movesProducerElementsIntoConsumerList() {
        List<OnlineCourse> source = List.of(
            new OnlineCourse(1, "Java Online", 32),
            new OnlineCourse(2, "Algorithms Online", 40)
        );
        List<Course> destination = new ArrayList<>();

        CopyUtils.copy(source, destination);

        assertEquals(2, destination.size());
        assertEquals("Java Online", destination.get(0).title());
    }

    @Test
    void copy_worksForObjectConsumer() {
        List<OnlineCourse> source = List.of(new OnlineCourse(1, "Java Online", 32));
        List<Object> destination = new ArrayList<>();

        CopyUtils.copy(source, destination);

        assertEquals(1, destination.size());
        assertEquals("Java Online", ((Course) destination.get(0)).title());
    }

    @Test
    void pecsWildcardsAllowReadingAndWriting() {
        List<OnlineCourse> source = List.of(new OnlineCourse(1, "Java Online", 32));
        List<? extends Course> producer = source;
        List<? super Course> consumer = new ArrayList<Object>();

        Course read = producer.get(0);
        consumer.add(new Course(2, "Databases", 30));

        assertEquals("Java Online", read.title());
        assertEquals(1, consumer.size());
    }
}
