package java1.task12;

/* Демонстрация sealed-иерархии и exhaustive switch. */
public class Main {

    public static void main(String[] args) {

        EnrollmentResult[] results = {
            new Accepted(1, 10),
            new Rejected(2, 10, "no seats"),
            new WaitListed(3, 10, 1)
        };

        for (EnrollmentResult result : results) {
            System.out.println(ResultPrinter.describe(result));
        }
    }
}