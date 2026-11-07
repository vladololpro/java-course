package java1;

import java1.task15.AgeRule;
import java1.task15.AllOfRule;
import java1.task15.AlwaysFailRule;
import java1.task15.AnyOfRule;
import java1.task15.CapacityRule;
import java1.task15.CourseActiveRule;
import java1.task15.DomainRule;
import java1.task15.EnrollmentRule;
import java1.task15.EnrollmentService;
import java1.task15.Failed;
import java1.task15.Passed;
import java1.task15.RuleResult;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Task15Test {

    @Test
    void passedAndFailed_exposeTheirResult() {
        RuleResult passed = new Passed("ok");
        RuleResult failed = new Failed("rejected");

        assertTrue(passed.isPassed());
        assertEquals("ok", passed.reason());
        assertFalse(failed.isPassed());
        assertEquals("rejected", failed.reason());
    }

    @Test
    void simpleRules_validateTheirOwnCondition() {
        assertTrue(new DomainRule().check(1, 20, 10).isPassed());
        assertFalse(new DomainRule().check(0, 20, 10).isPassed());
        assertTrue(new CourseActiveRule().check(1, 20, 10).isPassed());
        assertFalse(new CourseActiveRule().check(1, 20, 0).isPassed());
        assertTrue(new AgeRule(18).check(1, 18, 10).isPassed());
        assertFalse(new AgeRule(18).check(1, 17, 10).isPassed());
        assertFalse(new AlwaysFailRule().check(1, 20, 10).isPassed());
    }

    @Test
    void allOf_stopsAtFirstFailure() {
        AtomicInteger callsAfterFailure = new AtomicInteger();
        EnrollmentRule afterFailure = (studentId, studentAge, courseId) -> {
            callsAfterFailure.incrementAndGet();
            return new Passed("should not be called");
        };
        AllOfRule rule = new AllOfRule(List.of(
            (studentId, studentAge, courseId) -> new Passed("first"),
            (studentId, studentAge, courseId) -> new Failed("second"),
            afterFailure
        ));

        RuleResult result = rule.check(1, 20, 10);

        assertFalse(result.isPassed());
        assertEquals("second", result.reason());
        assertEquals(0, callsAfterFailure.get());
    }

    @Test
    void anyOf_passesWhenAtLeastOneRulePasses() {
        AnyOfRule rule = new AnyOfRule(List.of(
            new AlwaysFailRule(),
            new DomainRule()
        ));

        assertTrue(rule.check(1, 20, 10).isPassed());

        AnyOfRule allFail = new AnyOfRule(List.of(
            new AlwaysFailRule(),
            new AlwaysFailRule()
        ));

        RuleResult result = allFail.check(1, 20, 10);

        assertFalse(result.isPassed());
        assertTrue(result.reason().contains("always fail"));
    }

    @Test
    void capacityRule_countsOnlySuccessfulEnrollments() {
        CapacityRule rule = new CapacityRule(1);

        assertTrue(rule.check(1, 20, 10).isPassed());

        rule.enrollOne();

        assertFalse(rule.check(2, 20, 10).isPassed());
    }

    @Test
    void enrollmentService_worksThroughRuleInterface() {
        EnrollmentRule rule = new AllOfRule(List.of(
            new DomainRule(),
            new CourseActiveRule(),
            new AgeRule(18)
        ));
        EnrollmentService service = new EnrollmentService(rule);

        try (OutputCapture out = new OutputCapture()) {
            assertTrue(service.enroll(1, 20, 10));
            assertFalse(service.enroll(1, 16, 10));
            assertFalse(service.enroll(0, 20, 10));

            assertTrue(out.text().contains("OK:"));
            assertTrue(out.text().contains("NO:"));
        }
    }
}
