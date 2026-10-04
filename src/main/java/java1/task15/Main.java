package java1.task15;

import java.util.List;

/* Демонстрация расширяемого движка правил CourseHub.
   Архитектура:
   - EnrollmentRule - открытый интерфейс правила. Сервис работает только с ним, не зная конкретных классов.
   - RuleResult - sealed-иерархия результата, ровно два варианта: Passed и Failed. Компилятор проверяет исчерпывающий switch.
   - AllOfRule и AnyOfRule - композитные правила, сами реализуют EnrollmentRule и содержат список других правил. Их можно вкладывать друг в друга.
   - EnrollmentService - клиент. Принимает одно правило, вызывает check() и разбирает RuleResult через switch без default.

   В main три блока:
   1) basic - простое allOf из трёх правил (domain, courseActive, age). Показывает короткое замыкание: AllOfRule останавливается на первом Failed.
   2) nested - allOf с вложенным anyOf. Демонстрирует композицию композитов: anyOf проходит, если хотя бы одно из внутренних правил прошло.
   3) capacity - allOf с изменяемым CapacityRule. Один объект CapacityRule переиспользуется между вызовами, хранит внутри счётчик enrolled. После двух успешных попыток третья получает Failed.

   Dynamic dispatch: внутри AllOfRule и AnyOfRule вызов rule.check() идёт на разные объекты (DomainRule, AgeRule, AnyOfRule, ...), JVM выбирает реализацию по динамическому типу. */

public class Main {

    public static void main(String[] args) {

        EnrollmentRule basic = new AllOfRule(List.of(
            new DomainRule(),
            new CourseActiveRule(),
            new AgeRule(18)
        ));

        EnrollmentService basicService = new EnrollmentService(basic);

        System.out.println("--- basic ---");
        basicService.enroll(1, 20, 10);
        basicService.enroll(2, 16, 10);
        basicService.enroll(0, 20, 10);

        System.out.println();

        EnrollmentRule nested = new AllOfRule(List.of(
            new DomainRule(),
            new CourseActiveRule(),
            new AnyOfRule(List.of(
                new AgeRule(18),
                new AlwaysFailRule()
            ))
        ));

        EnrollmentService nestedService = new EnrollmentService(nested);

        System.out.println("--- nested ---");
        nestedService.enroll(1, 20, 10);
        nestedService.enroll(1, 10, 10);

        System.out.println();

        CapacityRule capacity = new CapacityRule(2);
        EnrollmentRule withCapacity = new AllOfRule(List.of(
            new AgeRule(18),
            capacity
        ));

        EnrollmentService capService = new EnrollmentService(withCapacity);

        System.out.println("--- capacity ---");
        if (capService.enroll(1, 20, 10)) capacity.enrollOne();
        if (capService.enroll(2, 20, 10)) capacity.enrollOne();
        capService.enroll(3, 20, 10);
    }
}