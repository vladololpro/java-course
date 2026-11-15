package java7;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Task4EnrollmentPipelineTest {

    @Test
    @Timeout(10)
    void allRequestsAreProcessedExactlyOnce() throws Exception {
        List<EnrollmentPipeline.EnrollmentRequest> requests = new ArrayList<>();
        for (int i = 1; i <= 200; i++) {
            requests.add(new EnrollmentPipeline.EnrollmentRequest(i, 1000 + i, "JAVA-101"));
        }

        EnrollmentPipeline pipeline = new EnrollmentPipeline();
        List<EnrollmentPipeline.ProcessedRequest> processed =
                pipeline.process(requests, 3, 2, 4);

        assertEquals(requests.size(), processed.size());

        Set<Long> processedIds = processed.stream()
                .map(EnrollmentPipeline.ProcessedRequest::requestId)
                .collect(Collectors.toSet());

        assertEquals(requests.size(), processedIds.size());
        assertTrue(processed.stream()
                .allMatch(request -> request.worker().startsWith("enrollment-worker-")));
    }
}
