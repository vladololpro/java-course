package java3.task02;

/* Точка входа для Задачки 2. */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== Problem: без equals/hashCode ===");
        SetDemo.showProblem();

        System.out.println();
        System.out.println("=== Solution: с equals/hashCode + LinkedHashSet ===");
        SetDemo.solve();
    }
}